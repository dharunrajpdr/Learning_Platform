package tests;

import entity.Skill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SkillServiceTest {
    @Test
    void acceptsValidSkill() {
        Skill skill = new Skill(1, "Java", "Technical", "Java programming");

        assertDoesNotThrow(skill::validate);
        assertEquals("Java", skill.getSkillName());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Skill(1, " ", "Technical", "Java programming"));
    }

    @Test
    void rejectsBlankCategory() {
        assertThrows(IllegalArgumentException.class,
                () -> new Skill(1, "Java", " ", "Java programming"));
    }
}
