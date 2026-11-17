INSERT INTO roles (role_name, description) VALUES
    ('Software Engineer', 'Builds and maintains software applications.'),
    ('Team Lead', 'Leads a delivery squad and manages technical execution.'),
    ('Business Analyst', 'Bridges business needs and technical solutions.'),
    ('HR Manager', 'Manages hiring, employee growth, and policies.'),
    ('L&D Admin', 'Coordinates learning programs and training delivery.');

INSERT INTO skills (skill_name, category, description) VALUES
    ('Java', 'Technical', 'Core Java programming and object-oriented skills.'),
    ('SQL', 'Technical', 'Relational database querying and schema understanding.'),
    ('Communication', 'Behavioral', 'Clear communication and stakeholder alignment.'),
    ('Leadership', 'Behavioral', 'Guiding and motivating teams to deliver outcomes.'),
    ('Project Management', 'Business', 'Planning, prioritization, and execution tracking.');

INSERT INTO employees (first_name, last_name, email, phone, role_id, hire_date) VALUES
    ('Aarav', 'Nair', 'aarav.nair@company.com', '+91-90001-00001', 1, '2022-01-10'),
    ('Bhavna', 'Reddy', 'bhavna.reddy@company.com', '+91-90001-00002', 1, '2021-05-15'),
    ('Chetan', 'Sharma', 'chetan.sharma@company.com', '+91-90001-00003', 2, '2020-11-20'),
    ('Divya', 'Iyer', 'divya.iyer@company.com', '+91-90001-00004', 3, '2023-02-18'),
    ('Ethan', 'Thomas', 'ethan.thomas@company.com', '+91-90001-00005', 5, '2021-08-09');

INSERT INTO role_skill_requirements (role_id, skill_id, required_level) VALUES
    (1, 1, 4),
    (1, 2, 3),
    (1, 3, 2),
    (2, 1, 3),
    (2, 3, 4),
    (2, 4, 5),
    (3, 2, 4),
    (3, 3, 5),
    (3, 5, 3),
    (4, 3, 4),
    (4, 5, 4),
    (5, 3, 3),
    (5, 5, 5);

INSERT INTO employee_skills (employee_id, skill_id, current_level) VALUES
    (1, 1, 3),
    (1, 2, 2),
    (1, 3, 2),
    (2, 1, 4),
    (2, 2, 2),
    (2, 3, 3),
    (3, 1, 4),
    (3, 3, 4),
    (3, 4, 3),
    (4, 2, 3),
    (4, 3, 5),
    (4, 5, 2),
    (5, 3, 3),
    (5, 5, 4);

INSERT INTO courses (course_name, description, capacity, status) VALUES
    ('Core Java Fundamentals', 'Java basics, OOP, and practical coding patterns.', 25, 'ACTIVE'),
    ('SQL for Data Analysis', 'Learn SQL joins, aggregations, and reporting use cases.', 18, 'ACTIVE'),
    ('Executive Communication Skills', 'Develop improved stakeholder communication and influence.', 20, 'ACTIVE'),
    ('Leadership Essentials', 'Lead teams, resolve conflict, and drive performance.', 15, 'ACTIVE'),
    ('Project Execution Bootcamp', 'A hands-on project planning and delivery bootcamp.', 30, 'ACTIVE');

INSERT INTO course_skills (course_id, skill_id, skill_level_gain) VALUES
    (1, 1, 1),
    (1, 2, 1),
    (2, 2, 1),
    (3, 3, 1),
    (4, 4, 1),
    (5, 5, 1),
    (5, 3, 1),
    (3, 1, 1),
    (4, 3, 1),
    (2, 5, 1);

INSERT INTO enrolments (employee_id, course_id, status, waitlist_position, enrolled_at, completed_at) VALUES
    (1, 1, 'COMPLETED', NULL, '2024-01-15 09:00:00', '2024-01-30 09:00:00'),
    (2, 1, 'ENROLLED', NULL, '2024-02-01 10:15:00', NULL),
    (3, 1, 'WAITLISTED', 2, '2024-02-03 11:00:00', NULL),
    (4, 2, 'ENROLLED', NULL, '2024-02-08 14:00:00', NULL),
    (5, 2, 'COMPLETED', NULL, '2024-01-12 08:30:00', '2024-01-25 08:30:00'),
    (1, 3, 'ENROLLED', NULL, '2024-02-05 09:45:00', NULL),
    (2, 3, 'COMPLETED', NULL, '2024-02-09 10:00:00', '2024-02-23 10:00:00'),
    (3, 3, 'ENROLLED', NULL, '2024-02-10 12:00:00', NULL),
    (4, 4, 'ENROLLED', NULL, '2024-02-14 09:15:00', NULL),
    (5, 4, 'WAITLISTED', 1, '2024-02-15 11:30:00', NULL),
    (1, 5, 'DROPPED', NULL, '2024-02-02 13:00:00', NULL),
    (2, 5, 'ENROLLED', NULL, '2024-02-11 15:00:00', NULL),
    (3, 5, 'COMPLETED', NULL, '2024-02-18 09:00:00', '2024-02-28 09:00:00'),
    (4, 5, 'ENROLLED', NULL, '2024-02-17 09:30:00', NULL),
    (5, 1, 'ENROLLED', NULL, '2024-02-19 15:45:00', NULL),
    (1, 2, 'WAITLISTED', 3, '2024-02-21 16:00:00', NULL);
