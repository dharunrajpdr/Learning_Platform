package entity;

import java.util.Objects;

public class CourseSkill {
    private int courseId;
    private int skillId;
    private int skillLevelGain;

    public CourseSkill() {
    }

    public CourseSkill(int courseId, int skillId, int skillLevelGain) {
        this.courseId = courseId;
        this.skillId = skillId;
        this.skillLevelGain = skillLevelGain;
        validate();
    }

    public void validate() {
        if (courseId <= 0) {
            throw new IllegalArgumentException("Course id must be greater than zero.");
        }
        if (skillId <= 0) {
            throw new IllegalArgumentException("Skill id must be greater than zero.");
        }
        if (skillLevelGain < 1 || skillLevelGain > 5) {
            throw new IllegalArgumentException("Skill level gain must be between 1 and 5.");
        }
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevelGain() {
        return skillLevelGain;
    }

    public void setSkillLevelGain(int skillLevelGain) {
        this.skillLevelGain = skillLevelGain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseSkill that = (CourseSkill) o;
        return courseId == that.courseId && skillId == that.skillId && skillLevelGain == that.skillLevelGain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, skillId, skillLevelGain);
    }
}
