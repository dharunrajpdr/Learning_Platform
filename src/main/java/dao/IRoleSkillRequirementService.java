package dao;

import entity.RoleSkillRequirement;
import exception.ProjectException;

import java.util.List;

public interface IRoleSkillRequirementService {
    RoleSkillRequirement create(RoleSkillRequirement requirement) throws ProjectException;
    List<RoleSkillRequirement> findByRoleId(int roleId) throws ProjectException;
    RoleSkillRequirement update(RoleSkillRequirement requirement) throws ProjectException;
    boolean delete(int roleId, int skillId) throws ProjectException;
}
