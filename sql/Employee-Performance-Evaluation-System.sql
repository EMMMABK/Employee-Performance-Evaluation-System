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

ALTER TABLE trash DROP CONSTRAINT trash_employee_id_fkey;
CREATE TABLE performance_reviews (
    id SERIAL PRIMARY KEY,  -- айди самой таблицы
    employee_id INT NOT NULL REFERENCES employees(id) ON DELETE CASCADE,  -- айди сотрудника (с удалением записи из performance_reviews при удалении сотрудника)
    review_date DATE NOT NULL,  -- дата оценки
    performance_score DOUBLE PRECISION CHECK (performance_score BETWEEN 1 AND 10),  -- средний балл от 1 до 10
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE  -- внешний ключ с каскадным удалением
);




