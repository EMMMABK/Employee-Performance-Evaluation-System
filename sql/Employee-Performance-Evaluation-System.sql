CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL
);


CREATE TABLE grades (
    id SERIAL PRIMARY KEY,
    employee_id INT REFERENCES employees(id) ON DELETE NO ACTION,
    grade FLOAT DEFAULT 0
);
ALTER TABLE grades ADD CONSTRAINT check_grade_max CHECK (grade <= 10.0);
ALTER TABLE grades DROP CONSTRAINT grades_employee_id_fkey;
ALTER TABLE grades ADD CONSTRAINT unique_employee_id UNIQUE (employee_id);


CREATE TABLE trash (
    id SERIAL PRIMARY KEY,
    employee_id INT REFERENCES employees(id) ON DELETE NO ACTION,
    name VARCHAR(100),
    department VARCHAR(50),
    hire_date DATE,
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE trash DROP CONSTRAINT trash_employee_id_fkey;
