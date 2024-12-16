CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL
);

CREATE TABLE grades (
    id SERIAL PRIMARY KEY,
    employee_id INT,
    grade FLOAT DEFAULT 0,
    CONSTRAINT fk_employee_grades FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE, 
    CONSTRAINT check_grade_max CHECK (grade <= 10.0),
    CONSTRAINT unique_employee_id UNIQUE (employee_id)
);

CREATE TABLE trash (
    id SERIAL PRIMARY KEY,
    employee_id INT,
    name VARCHAR(100),
    department VARCHAR(50),
    grade FLOAT,
    hire_date DATE,
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee_grades (
    employee_id INT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(50),
    hire_date DATE,
    grade FLOAT,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);


CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    CHECK (end_date IS NULL OR end_date > start_date)
);

