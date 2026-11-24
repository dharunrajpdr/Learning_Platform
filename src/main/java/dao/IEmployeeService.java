package dao;

import entity.Employee;
import exception.ProjectException;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee create(Employee employee) throws ProjectException;
    Optional<Employee> findById(int employeeId) throws ProjectException;
    List<Employee> findAll() throws ProjectException;
    Employee update(Employee employee) throws ProjectException;
    boolean delete(int employeeId) throws ProjectException;
    Employee assignRole(int employeeId, int roleId) throws ProjectException;
}
