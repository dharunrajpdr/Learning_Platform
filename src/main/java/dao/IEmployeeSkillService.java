package dao;

import entity.EmployeeSkill;
import exception.ProjectException;

import java.util.List;
import java.util.Optional;

public interface IEmployeeSkillService {
    EmployeeSkill add(EmployeeSkill employeeSkill) throws ProjectException;
    Optional<EmployeeSkill> find(int employeeId, int skillId) throws ProjectException;
    List<EmployeeSkill> findByEmployeeId(int employeeId) throws ProjectException;
    EmployeeSkill update(EmployeeSkill employeeSkill) throws ProjectException;
    boolean remove(int employeeId, int skillId) throws ProjectException;
}
