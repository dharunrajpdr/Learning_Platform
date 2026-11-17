package entity;

import java.util.Objects;

public class Skill {
    private int skillId;
    private String skillName;
    private String category;
    private String description;

    public Skill() {
    }

    public Skill(int skillId, String skillName, String category, String description) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.category = category;
        this.description = description;
        validate();
    }

    public void validate() {
        if (skillName == null || skillName.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name is required.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill category is required.");
        }
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return skillId == skill.skillId && Objects.equals(skillName, skill.skillName) && Objects.equals(category, skill.category) && Objects.equals(description, skill.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, skillName, category, description);
    }
}
