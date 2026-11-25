package dao;

import entity.EmployeeSkill;
import exception.DatabaseOperationException;
import exception.InvalidSkillLevelException;
import exception.RecordNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeSkillService implements IEmployeeSkillService {
    @Override
    public EmployeeSkill add(EmployeeSkill employeeSkill)
            throws DatabaseOperationException, InvalidSkillLevelException {
        validate(employeeSkill);
        String sql = "INSERT INTO employee_skills (employee_id, skill_id, current_level) "
                + "VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, employeeSkill);
            statement.executeUpdate();
            return employeeSkill;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to add employee skill; verify employee and skill ids.", exception);
        }
    }

    @Override
    public Optional<EmployeeSkill> find(int employeeId, int skillId)
            throws DatabaseOperationException {
        String sql = "SELECT employee_id, skill_id, current_level, last_updated "
                + "FROM employee_skills WHERE employee_id = ? AND skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.setInt(2, skillId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(map(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve employee skill.", exception);
        }
    }

    @Override
    public List<EmployeeSkill> findByEmployeeId(int employeeId)
            throws DatabaseOperationException {
        String sql = "SELECT employee_id, skill_id, current_level, last_updated "
                + "FROM employee_skills WHERE employee_id = ? ORDER BY skill_id";
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employeeSkills.add(map(resultSet));
                }
            }
            return employeeSkills;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to retrieve employee skill profile.", exception);
        }
    }

    @Override
    public EmployeeSkill update(EmployeeSkill employeeSkill)
            throws DatabaseOperationException, InvalidSkillLevelException,
            RecordNotFoundException {
        validate(employeeSkill);
        String sql = "UPDATE employee_skills SET current_level = ? "
                + "WHERE employee_id = ? AND skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeSkill.getCurrentLevel());
            statement.setInt(2, employeeSkill.getEmployeeId());
            statement.setInt(3, employeeSkill.getSkillId());
            if (statement.executeUpdate() == 0) {
                throw new RecordNotFoundException("Employee skill not found.");
            }
            return employeeSkill;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to update employee skill.", exception);
        }
    }

    @Override
    public boolean remove(int employeeId, int skillId) throws DatabaseOperationException {
        String sql = "DELETE FROM employee_skills WHERE employee_id = ? AND skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.setInt(2, skillId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to remove employee skill.", exception);
        }
    }

    private void validate(EmployeeSkill employeeSkill)
            throws DatabaseOperationException, InvalidSkillLevelException {
        if (employeeSkill == null) {
            throw new DatabaseOperationException("Employee skill is required.");
        }
        if (employeeSkill.getCurrentLevel() < 1 || employeeSkill.getCurrentLevel() > 5) {
            throw new InvalidSkillLevelException(
                    "Employee skill level must be between 1 and 5.");
        }
        try {
            employeeSkill.validate();
        } catch (IllegalArgumentException exception) {
            throw new DatabaseOperationException(
                    "Employee skill data is invalid: " + exception.getMessage(), exception);
        }
    }

    private void bind(PreparedStatement statement, EmployeeSkill employeeSkill)
            throws SQLException {
        statement.setInt(1, employeeSkill.getEmployeeId());
        statement.setInt(2, employeeSkill.getSkillId());
        statement.setInt(3, employeeSkill.getCurrentLevel());
    }

    private EmployeeSkill map(ResultSet resultSet) throws SQLException {
        Timestamp lastUpdated = resultSet.getTimestamp("last_updated");
        return new EmployeeSkill(
                resultSet.getInt("employee_id"),
                resultSet.getInt("skill_id"),
                resultSet.getInt("current_level"),
                lastUpdated == null ? null : lastUpdated.toLocalDateTime());
    }
}
