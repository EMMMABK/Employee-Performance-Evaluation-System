--1. Retrieve a list of employees with their names and grades.
SELECT e.name, g.grade
FROM employees e
LEFT JOIN grades g ON e.id = g.employee_id;
--2. Find the average grade of employees in a specific department.
SELECT department, AVG(g.grade) AS average_grade
FROM employees e
JOIN grades g ON e.id = g.employee_id
WHERE e.department = 'specific_department'
GROUP BY e.department;
--3. List employees who have a grade higher than a specific value.
SELECT e.name, g.grade
FROM employees e
JOIN grades g ON e.id = g.employee_id
WHERE g.grade > specific_value;
--4. Find the top N employees with the highest grades.
SELECT e.name, g.grade
FROM employees e
JOIN grades g ON e.id = g.employee_id
ORDER BY g.grade DESC
LIMIT N;
--5. Identify departments with the highest average grades.
SELECT department, AVG(g.grade) AS average_grade
FROM employees e
JOIN grades g ON e.id = g.employee_id
GROUP BY e.department
ORDER BY average_grade DESC
LIMIT 1;
--6. Retrieve employees with the highest grade in each department.
SELECT e.department, e.name, g.grade
FROM employees e
JOIN grades g ON e.id = g.employee_id
WHERE g.grade = (
    SELECT MAX(g2.grade)
    FROM grades g2
    JOIN employees e2 ON g2.employee_id = e2.id
    WHERE e2.department = e.department
);
--7. Identify employees who are not assigned a grade.
SELECT e.name
FROM employees e
LEFT JOIN grades g ON e.id = g.employee_id
WHERE g.grade IS NULL;
--8. Retrieve the most recently deleted employees from the trash table.
SELECT name, department, grade, hire_date, deleted_at
FROM trash
ORDER BY deleted_at DESC;
--9. Calculate the total number of completed projects.
SELECT COUNT(*) AS total_completed_projects
FROM projects
WHERE end_date IS NOT NULL;
-- 10. List projects that do not have a defined end date (incomplete projects).
SELECT id, title, description, start_date
FROM projects
WHERE end_date IS NULL;
