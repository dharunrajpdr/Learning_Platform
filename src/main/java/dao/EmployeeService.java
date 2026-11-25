package dao;

import entity.Employee;
import exception.DatabaseOperationException;
import exception.RecordNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService implements IEmployeeService {
    @Override
    public Employee create(Employee employee) throws DatabaseOperationException {
        validate(employee);
        String sql = "INSERT INTO employees "
                + "(first_name, last_name, email, phone, role_id, hire_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(statement, employee);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new DatabaseOperationException(
                            "Employee was created but no generated id was returned.");
                }
                employee.setEmployeeId(keys.getInt(1));
            }
            return employee;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to create employee.", exception);
        }
    }

    @Override
    public Optional<Employee> findById(int employeeId) throws DatabaseOperationException {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, role_id, hire_date "
                + "FROM employees WHERE employee_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(map(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve employee.", exception);
        }
    }

    @Override
    public List<Employee> findAll() throws DatabaseOperationException {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, role_id, hire_date "
                + "FROM employees ORDER BY last_name, first_name";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(map(resultSet));
            }
            return employees;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve employees.", exception);
        }
    }

    @Override
    public Employee update(Employee employee)
            throws DatabaseOperationException, RecordNotFoundException {
        validate(employee);
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, "
                + "phone = ?, role_id = ?, hire_date = ? WHERE employee_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, employee);
            statement.setInt(7, employee.getEmployeeId());
            if (statement.executeUpdate() == 0) {
                throw new RecordNotFoundException(
                        "Employee not found: " + employee.getEmployeeId());
            }
            return employee;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to update employee.", exception);
        }
    }

    @Override
    public boolean delete(int employeeId) throws DatabaseOperationException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to delete employee; the employee may have related records.",
                    exception);
        }
    }

    @Override
    public Employee assignRole(int employeeId, int roleId)
            throws DatabaseOperationException, RecordNotFoundException {
        if (roleId <= 0) {
            throw new DatabaseOperationException("Role id must be greater than zero.");
        }
        String sql = "UPDATE employees SET role_id = ? WHERE employee_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            statement.setInt(2, employeeId);
            if (statement.executeUpdate() == 0) {
                throw new RecordNotFoundException("Employee not found: " + employeeId);
            }
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to assign role; verify that the role exists.", exception);
        }
        return findById(employeeId).orElseThrow(
                () -> new RecordNotFoundException("Employee not found: " + employeeId));
    }

    private void validate(Employee employee) throws DatabaseOperationException {
        if (employee == null) {
            throw new DatabaseOperationException("Employee is required.");
        }
        employee.validate();
    }

    private void bind(PreparedStatement statement, Employee employee) throws SQLException {
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getPhone());
        statement.setInt(5, employee.getRoleId());
        statement.setDate(6, Date.valueOf(employee.getHireDate()));
    }

    private Employee map(ResultSet resultSet) throws SQLException {
        Date hireDate = resultSet.getDate("hire_date");
        return new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getInt("role_id"),
                hireDate.toLocalDate());
    }
}
