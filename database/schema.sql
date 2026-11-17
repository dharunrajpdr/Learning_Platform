-- ER DIAGRAM (Mermaid)
-- erDiagram
--   ROLE ||--o{ EMPLOYEE : assigns
--   ROLE ||--o{ ROLE_SKILL_REQUIREMENT : requires
--   SKILL ||--o{ ROLE_SKILL_REQUIREMENT : is_required_for
--   EMPLOYEE ||--o{ EMPLOYEE_SKILL : has
--   SKILL ||--o{ EMPLOYEE_SKILL : is_mastered_by
--   COURSE ||--o{ COURSE_SKILL : maps_to
--   SKILL ||--o{ COURSE_SKILL : is_taught_by
--   EMPLOYEE ||--o{ ENROLMENT : enrolls_in
--   COURSE ||--o{ ENROLMENT : has_enrolments

CREATE TABLE IF NOT EXISTS roles (
    role_id INT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    PRIMARY KEY (role_id),
    UNIQUE KEY uq_roles_role_name (role_name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS skills (
    skill_id INT NOT NULL AUTO_INCREMENT,
    skill_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
    PRIMARY KEY (skill_id),
    UNIQUE KEY uq_skills_skill_name (skill_name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS employees (
    employee_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    email VARCHAR(120) NOT NULL,
    phone VARCHAR(20) NULL,
    role_id INT NOT NULL,
    hire_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    PRIMARY KEY (employee_id),
    UNIQUE KEY uq_employees_email (email),
    CONSTRAINT fk_employees_role
        FOREIGN KEY (role_id) REFERENCES roles (role_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_employees_role_id ON employees (role_id);

CREATE TABLE IF NOT EXISTS role_skill_requirements (
    role_id INT NOT NULL,
    skill_id INT NOT NULL,
    required_level TINYINT NOT NULL,
    PRIMARY KEY (role_id, skill_id),
    CONSTRAINT chk_role_skill_required_level
        CHECK (required_level BETWEEN 1 AND 5),
    CONSTRAINT fk_role_skill_requirements_role
        FOREIGN KEY (role_id) REFERENCES roles (role_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_role_skill_requirements_skill
        FOREIGN KEY (skill_id) REFERENCES skills (skill_id)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS employee_skills (
    employee_id INT NOT NULL,
    skill_id INT NOT NULL,
    current_level TINYINT NOT NULL,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (employee_id, skill_id),
    CONSTRAINT chk_employee_skills_level
        CHECK (current_level BETWEEN 1 AND 5),
    CONSTRAINT fk_employee_skills_employee
        FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_employee_skills_skill
        FOREIGN KEY (skill_id) REFERENCES skills (skill_id)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS courses (
    course_id INT NOT NULL AUTO_INCREMENT,
    course_name VARCHAR(150) NOT NULL,
    description VARCHAR(255) NULL,
    capacity INT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (course_id),
    UNIQUE KEY uq_courses_course_name (course_name),
    CONSTRAINT chk_courses_capacity CHECK (capacity > 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS course_skills (
    course_id INT NOT NULL,
    skill_id INT NOT NULL,
    skill_level_gain TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (course_id, skill_id),
    CONSTRAINT chk_course_skills_gain
        CHECK (skill_level_gain BETWEEN 1 AND 5),
    CONSTRAINT fk_course_skills_course
        FOREIGN KEY (course_id) REFERENCES courses (course_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_course_skills_skill
        FOREIGN KEY (skill_id) REFERENCES skills (skill_id)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS enrolments (
    enrolment_id INT NOT NULL AUTO_INCREMENT,
    employee_id INT NOT NULL,
    course_id INT NOT NULL,
    status ENUM('WAITLISTED', 'ENROLLED', 'COMPLETED', 'DROPPED') NOT NULL DEFAULT 'ENROLLED',
    waitlist_position INT NULL,
    enrolled_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    PRIMARY KEY (enrolment_id),
    UNIQUE KEY uq_enrolments_employee_course (employee_id, course_id),
    CONSTRAINT chk_enrolments_waitlist_position
        CHECK (waitlist_position IS NULL OR waitlist_position > 0),
    CONSTRAINT fk_enrolments_employee
        FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_enrolments_course
        FOREIGN KEY (course_id) REFERENCES courses (course_id)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_enrolments_course_status ON enrolments (course_id, status);
CREATE INDEX idx_enrolments_employee_status ON enrolments (employee_id, status);
