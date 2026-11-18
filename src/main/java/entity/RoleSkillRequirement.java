package entity;

import java.util.Objects;

public class RoleSkillRequirement {
    private int roleId;
    private int skillId;
    private int requiredLevel;

    public RoleSkillRequirement() {
    }

    public RoleSkillRequirement(int roleId, int skillId, int requiredLevel) {
        this.roleId = roleId;
        this.skillId = skillId;
        this.requiredLevel = requiredLevel;
        validate();
    }

    public void validate() {
        if (roleId <= 0) {
            throw new IllegalArgumentException("Role id must be greater than zero.");
        }
        if (skillId <= 0) {
            throw new IllegalArgumentException("Skill id must be greater than zero.");
        }
        if (requiredLevel < 1 || requiredLevel > 5) {
            throw new IllegalArgumentException("Required level must be between 1 and 5.");
        }
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleSkillRequirement that = (RoleSkillRequirement) o;
        return roleId == that.roleId && skillId == that.skillId && requiredLevel == that.requiredLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, skillId, requiredLevel);
    }
}
