package tests;

import entity.Employee;
import entity.EmployeeSkill;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeManagementTest {
    @Test
    void createsEmployeeWithValidDetails() {
        Employee employee = new Employee(
                1, "Aarav", "Nair", "aarav@company.com",
                null, 2, LocalDate.of(2022, 1, 10));

        assertEquals(2, employee.getRoleId());
        assertEquals("aarav@company.com", employee.getEmail());
    }

    @Test
    void rejectsEmployeeWithoutValidEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> new Employee(
                        1, "Aarav", "Nair", "invalid-email",
                        null, 2, LocalDate.of(2022, 1, 10)));
    }

    @Test
    void acceptsEmployeeSkillAtLevelBoundaries() {
        EmployeeSkill minimum = new EmployeeSkill(1, 1, 1, LocalDateTime.now());
        EmployeeSkill maximum = new EmployeeSkill(1, 2, 5, LocalDateTime.now());

        minimum.validate();
        maximum.validate();
    }

    @Test
    void rejectsEmployeeSkillAboveMaximum() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmployeeSkill(1, 1, 6, LocalDateTime.now()));
    }
}
