package tests;

import exception.CourseFullException;
import exception.DatabaseOperationException;
import exception.DuplicateEnrolmentException;
import exception.InvalidCourseException;
import exception.InvalidSkillLevelException;
import exception.ProjectException;
import exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectExceptionTest {
    @Test
    void customExceptionsShareBaseType() {
        assertInstanceOf(ProjectException.class,
                new RecordNotFoundException("Not found"));
        assertInstanceOf(ProjectException.class,
                new CourseFullException("Full"));
        assertInstanceOf(ProjectException.class,
                new DuplicateEnrolmentException("Duplicate"));
        assertInstanceOf(ProjectException.class,
                new InvalidSkillLevelException("Invalid level"));
        assertInstanceOf(ProjectException.class,
                new InvalidCourseException("Invalid course"));
    }

    @Test
    void databaseExceptionPreservesCause() {
        Throwable cause = new IllegalStateException("Database unavailable");
        DatabaseOperationException exception =
                new DatabaseOperationException("Database operation failed", cause);

        assertEquals("Database operation failed", exception.getMessage());
        assertTrue(exception.getCause() == cause);
    }
}
