package org.example.employeeperformanceapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements EmployeeDAOInterface {
    private final Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (name, department, hire_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setDate(3, new java.sql.Date(employee.getHireDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        String query = "UPDATE employees SET name = ?, department = ?, hire_date = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setDate(3, new java.sql.Date(employee.getHireDate().getTime()));
            statement.setInt(4, employee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Новый метод для перемещения сотрудника в таблицу trash
    public void moveToTrash(int id) {
        String selectSql = "SELECT * FROM employees WHERE id = ?";
        String insertSql = "INSERT INTO trash (employee_id, name, department, hire_date) SELECT id, name, department, hire_date FROM employees WHERE id = ?";
        String deleteSql = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {

            // Перемещаем данные из employees в trash
            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                insertStmt.setInt(1, id);
                insertStmt.executeUpdate();
                deleteStmt.setInt(1, id);
                deleteStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void restoreFromTrash(int id) {
        String selectSql = "SELECT * FROM trash WHERE id = ?";
        String restoreSql = "INSERT INTO employees (id, name, department, hire_date) SELECT employee_id, name, department, hire_date FROM trash WHERE employee_id = ?";
        String deleteSql = "DELETE FROM trash WHERE employee_id = ?";
        try (PreparedStatement restoreStmt = connection.prepareStatement(restoreSql);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {

            // Перемещаем данные из trash обратно в employees
            restoreStmt.setInt(1, id);
            restoreStmt.executeUpdate();

            // Удаляем запись из trash
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Метод для получения всех сотрудников из trash
    public List<Employee> getAllFromTrash() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM trash";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                Date hireDate = rs.getDate("hire_date");
                employees.add(new Employee(id, name, department, hireDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getDate("hire_date")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        String query = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getDate("hire_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}