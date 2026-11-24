package tests;

import entity.Role;
import entity.RoleSkillRequirement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleServiceTest {
    @Test
    void acceptsValidRole() {
        Role role = new Role(1, "Software Engineer", "Develops applications");

        assertDoesNotThrow(role::validate);
        assertEquals("Software Engineer", role.getRoleName());
    }

    @Test
    void rejectsBlankRoleName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Role(1, " ", "Develops applications"));
    }

    @Test
    void acceptsRequiredLevelWithinRange() {
        RoleSkillRequirement requirement =
                new RoleSkillRequirement(1, 2, 5);

        assertDoesNotThrow(requirement::validate);
        assertEquals(5, requirement.getRequiredLevel());
    }

    @Test
    void rejectsRequiredLevelAboveRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoleSkillRequirement(1, 2, 6));
    }
}
