-- Example 1: Successful transaction with commit
START TRANSACTION;

INSERT INTO enrolments (employee_id, course_id, status, waitlist_position)
VALUES (2, 4, 'ENROLLED', NULL);

INSERT INTO enrolments (employee_id, course_id, status, waitlist_position)
VALUES (5, 3, 'WAITLISTED', 4);

COMMIT;

-- Example 2: Transaction rollback on failed course registration
START TRANSACTION;

-- Business rule: duplicate enrolment must not be allowed.
-- This example is intentionally rejected by the UNIQUE constraint.
INSERT INTO enrolments (employee_id, course_id, status)
VALUES (1, 1, 'ENROLLED');

-- If the insert fails, issue a rollback to keep data consistent.
ROLLBACK;

-- Example 3: Multi-step capacity and waitlist transaction
START TRANSACTION;

SELECT COUNT(*) AS enrolled_count
FROM enrolments
WHERE course_id = 2 AND status IN ('ENROLLED', 'COMPLETED');

-- Suppose capacity is 18 and enrolled_count is 18.
-- The system should place a new employee on the waitlist instead of inserting as enrolled.
INSERT INTO enrolments (employee_id, course_id, status, waitlist_position)
VALUES (4, 2, 'WAITLISTED', 1);

COMMIT;

-- Example 4: Savepoint-based rollback for partial changes
START TRANSACTION;

SAVEPOINT before_course_update;

UPDATE courses
SET capacity = 12
WHERE course_id = 1;

-- Revert only the latest step if validation fails
ROLLBACK TO SAVEPOINT before_course_update;

COMMIT;

-- Example 5: Full enrolment lifecycle demonstration
START TRANSACTION;

INSERT INTO enrolments (employee_id, course_id, status)
VALUES (1, 4, 'ENROLLED');

UPDATE enrolments
SET status = 'COMPLETED', completed_at = CURRENT_TIMESTAMP
WHERE employee_id = 1 AND course_id = 4;

COMMIT;
