package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class EmployeeSkill {
    private int employeeId;
    private int skillId;
    private int currentLevel;
    private LocalDateTime lastUpdated;

    public EmployeeSkill() {
    }

    public EmployeeSkill(int employeeId, int skillId, int currentLevel, LocalDateTime lastUpdated) {
        this.employeeId = employeeId;
        this.skillId = skillId;
        this.currentLevel = currentLevel;
        this.lastUpdated = lastUpdated;
        validate();
    }

    public void validate() {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee id must be greater than zero.");
        }
        if (skillId <= 0) {
            throw new IllegalArgumentException("Skill id must be greater than zero.");
        }
        if (currentLevel < 1 || currentLevel > 5) {
            throw new IllegalArgumentException("Current skill level must be between 1 and 5.");
        }
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeSkill that = (EmployeeSkill) o;
        return employeeId == that.employeeId && skillId == that.skillId && currentLevel == that.currentLevel && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, skillId, currentLevel, lastUpdated);
    }
}
