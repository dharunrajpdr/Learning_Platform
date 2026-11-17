CREATE OR REPLACE VIEW vw_employee_skill_gap AS
SELECT
    e.employee_id,
    e.first_name,
    e.last_name,
    r.role_id,
    r.role_name,
    s.skill_id,
    s.skill_name,
    rs.required_level,
    COALESCE(es.current_level, 0) AS current_level,
    CASE
        WHEN COALESCE(es.current_level, 0) >= rs.required_level THEN 0
        ELSE rs.required_level - COALESCE(es.current_level, 0)
    END AS skill_gap
FROM employees e
JOIN roles r ON e.role_id = r.role_id
JOIN role_skill_requirements rs ON r.role_id = rs.role_id
JOIN skills s ON rs.skill_id = s.skill_id
LEFT JOIN employee_skills es
    ON es.employee_id = e.employee_id
   AND es.skill_id = s.skill_id;

CREATE OR REPLACE VIEW vw_course_completion_report AS
SELECT
    c.course_id,
    c.course_name,
    COUNT(e.enrolment_id) AS total_enrolments,
    SUM(CASE WHEN e.status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_count,
    ROUND(
        (SUM(CASE WHEN e.status = 'COMPLETED' THEN 1 ELSE 0 END) / COUNT(e.enrolment_id)) * 100,
        2
    ) AS completion_rate_percent
FROM courses c
LEFT JOIN enrolments e ON e.course_id = c.course_id
GROUP BY c.course_id, c.course_name;

CREATE OR REPLACE VIEW vw_most_required_skills AS
SELECT
    s.skill_id,
    s.skill_name,
    COUNT(rsr.skill_id) AS required_frequency
FROM skills s
JOIN role_skill_requirements rsr ON rsr.skill_id = s.skill_id
GROUP BY s.skill_id, s.skill_name
ORDER BY required_frequency DESC, s.skill_name ASC;

SELECT
    employee_id,
    first_name,
    last_name,
    role_name,
    skill_name,
    required_level,
    current_level,
    skill_gap
FROM vw_employee_skill_gap
WHERE employee_id = 1
ORDER BY skill_gap DESC, skill_name;

SELECT
    course_name,
    total_enrolments,
    completed_count,
    completion_rate_percent
FROM vw_course_completion_report
ORDER BY completion_rate_percent DESC, course_name ASC;

SELECT
    skill_name,
    required_frequency
FROM vw_most_required_skills
ORDER BY required_frequency DESC, skill_name ASC;
