CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    hire_date DATE NOT null
);

CREATE TABLE trash (
    id SERIAL PRIMARY KEY,
    employee_id INT REFERENCES employees(id) ON DELETE NO ACTION,
    name VARCHAR(100),
    department VARCHAR(50),
    hire_date DATE,
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE grades (
    id SERIAL PRIMARY KEY,  -- айди записи
    employee_id INT NOT NULL REFERENCES employees(id) ON DELETE CASCADE,  -- айди сотрудника (с удалением записи при удалении сотрудника)
    attendance INT CHECK (attendance >= 0 AND attendance <= 10),  -- оценка по attendance (от 0 до 10)
    hard_skills INT CHECK (hard_skills >= 0 AND hard_skills <= 10),  -- оценка по hard skills (от 0 до 10)
    soft_skills INT CHECK (soft_skills >= 0 AND soft_skills <= 10),  -- оценка по soft skills (от 0 до 10)
    average_score DOUBLE PRECISION,  -- среднее арифметическое по трем оценкам
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE  -- внешний ключ с каскадным удалением
);

select * from employees;




