-- Сначала создаем таблицу buildings
CREATE TABLE buildings (
    dept_name VARCHAR(50) PRIMARY KEY, -- Уникальное имя отдела
    address TEXT NOT NULL,             -- Адрес здания
    manager_name VARCHAR(100) NOT NULL -- Имя менеджера здания
);

-- Теперь создаем таблицу employees
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL REFERENCES buildings(dept_name) ON DELETE CASCADE,
    hire_date DATE NOT NULL
);

-- Таблица trash
CREATE TABLE trash (
    id SERIAL PRIMARY KEY,
    employee_id INT REFERENCES employees(id) ON DELETE NO ACTION,
    name VARCHAR(100),
    department VARCHAR(50),
    hire_date DATE,
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Удаляем ограничение внешнего ключа из таблицы trash, если оно существует
ALTER TABLE trash DROP CONSTRAINT IF EXISTS trash_employee_id_fkey;

-- Таблица performance_reviews
CREATE TABLE performance_reviews (
    id SERIAL PRIMARY KEY,  -- айди самой таблицы
    employee_id INT NOT NULL REFERENCES employees(id) ON DELETE CASCADE,  -- айди сотрудника
    review_date DATE NOT NULL,  -- дата оценки
    performance_score DOUBLE PRECISION CHECK (performance_score BETWEEN 1 AND 10),  -- средний балл от 1 до 10
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE  -- внешний ключ
);


SELECT * FROM buildings;
SELECT * FROM employees;
SELECT * FROM trash;
SELECT * FROM performance_reviews;