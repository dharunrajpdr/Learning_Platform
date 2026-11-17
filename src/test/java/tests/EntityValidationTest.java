package tests;

import entity.Course;
import entity.CourseSkill;
import entity.Employee;
import entity.EmployeeSkill;
import entity.Enrolment;
import entity.Role;
import entity.RoleSkillRequirement;
import entity.Skill;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntityValidationTest {

    @Test
    void employeeRejectsBlankFirstName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Employee(1, " ", "Nair", "aarav@company.com", "999", 1, LocalDate.now()));
    }

    @Test
    void roleRejectsBlankRoleName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Role(1, " ", "Role description"));
    }

    @Test
    void skillRequiresCategory() {
        assertThrows(IllegalArgumentException.class,
                () -> new Skill(1, "Java", " ", "Programming language"));
    }

    @Test
    void roleSkillRequirementRejectsInvalidLevel() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoleSkillRequirement(1, 2, 6));
    }

    @Test
    void employeeSkillRejectsLevelOutOfRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmployeeSkill(1, 1, 0, LocalDateTime.now()));
    }

    @Test
    void courseRejectsInvalidCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> new Course(1, "Java Basics", "Intro", 0, "ACTIVE"));
    }

    @Test
    void courseSkillRejectsInvalidGain() {
        assertThrows(IllegalArgumentException.class,
                () -> new CourseSkill(1, 1, 0));
    }

    @Test
    void enrolmentRejectsInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enrolment(1, 1, 1, "INVALID", null, LocalDateTime.now(), null));
    }
}
