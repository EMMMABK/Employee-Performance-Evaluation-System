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
        String query = "DELETE FROM trash WHERE employee_id = ?";
        String delete_grade_query = "DELETE FROM GRADES WHERE employee_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(delete_grade_query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void moveToTrash(int id) {
        String selectEmployeeSql = "SELECT * FROM employees WHERE id = ?";
        String selectGradeSql = "SELECT grade FROM grades WHERE employee_id = ?";
        String insertTrashSql = "INSERT INTO trash (employee_id, name, department, grade, hire_date, deleted_at) " +
                "SELECT id, name, department, ?, hire_date, CURRENT_TIMESTAMP FROM employees WHERE id = ?";
        String deleteFromGradesSql = "DELETE FROM grades WHERE employee_id = ?";
        String deleteFromEmployeesSql = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement selectEmployeeStmt = connection.prepareStatement(selectEmployeeSql);
             PreparedStatement selectGradeStmt = connection.prepareStatement(selectGradeSql);
             PreparedStatement insertTrashStmt = connection.prepareStatement(insertTrashSql);
             PreparedStatement deleteFromGradesStmt = connection.prepareStatement(deleteFromGradesSql);
             PreparedStatement deleteFromEmployeesStmt = connection.prepareStatement(deleteFromEmployeesSql)) {

            selectEmployeeStmt.setInt(1, id);
            ResultSet employeeRs = selectEmployeeStmt.executeQuery();

            if (employeeRs.next()) {
                selectGradeStmt.setInt(1, id);
                ResultSet gradeRs = selectGradeStmt.executeQuery();
                float grade = 0;
                if (gradeRs.next()) {
                    grade = gradeRs.getFloat("grade");
                }

                System.out.println("Moving employee ID " + id + " to trash...");
                System.out.println("Employee name: " + employeeRs.getString("name"));
                System.out.println("Grade: " + grade);

                insertTrashStmt.setFloat(1, grade);
                insertTrashStmt.setInt(2, id);
                int rowsInserted = insertTrashStmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Employee with ID " + id + " successfully moved to trash.");
                } else {
                    System.out.println("Failed to insert employee into trash.");
                }

                deleteFromGradesStmt.setInt(1, id);
                int rowsDeletedFromGrades = deleteFromGradesStmt.executeUpdate();
                if (rowsDeletedFromGrades > 0) {
                    System.out.println("Grade for employee with ID " + id + " successfully deleted.");
                } else {
                    System.out.println("Failed to delete grade for employee with ID " + id);
                }

                deleteFromEmployeesStmt.setInt(1, id);
                int rowsDeletedFromEmployees = deleteFromEmployeesStmt.executeUpdate();
                if (rowsDeletedFromEmployees > 0) {
                    System.out.println("Employee with ID " + id + " successfully deleted from employees.");
                } else {
                    System.out.println("Failed to delete employee with ID " + id);
                }

            } else {
                System.out.println("Employee with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            System.err.println("Error while moving employee to trash: " + e.getMessage());
            e.printStackTrace();
        }
    }





    public void restoreFromTrash(int id) throws SQLException {
        String checkEmployeeSql = "SELECT COUNT(*) FROM employees WHERE id = ?";
        String restoreEmployeeSql = "INSERT INTO employees (id, name, department, hire_date) " +
                "SELECT employee_id, name, department, hire_date FROM trash WHERE employee_id = ?";
        String restoreGradeSql = "INSERT INTO grades (employee_id, grade) " +
                "SELECT employee_id, grade FROM trash WHERE employee_id = ?";
        String deleteSql = "DELETE FROM trash WHERE employee_id = ?";

        try (PreparedStatement checkEmployeeStmt = connection.prepareStatement(checkEmployeeSql);
             PreparedStatement restoreEmployeeStmt = connection.prepareStatement(restoreEmployeeSql);
             PreparedStatement restoreGradeStmt = connection.prepareStatement(restoreGradeSql);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {

            checkEmployeeStmt.setInt(1, id);
            try (ResultSet rs = checkEmployeeStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("An employee with this ID already exists in the employees table!");
                }
            }

            restoreEmployeeStmt.setInt(1, id);
            restoreEmployeeStmt.executeUpdate();

            restoreGradeStmt.setInt(1, id);
            restoreGradeStmt.executeUpdate();

            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            System.out.println("Employee with ID " + id + " successfully restored from trash.");
        } catch (SQLException e) {
            System.err.println("Error while restoring employee from trash: " + e.getMessage());
            throw e;
        }
    }



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

    public List<EmployeeGrade> getAllGrades() {
        List<EmployeeGrade> grades = new ArrayList<>();
        String query = """
        SELECT e.id, e.name, e.department, e.hire_date, g.grade
        FROM employees e
        LEFT JOIN grades g ON e.id = g.employee_id
    """;
        String insertSql = """
        INSERT INTO employee_grades (employee_id, name, department, hire_date, grade)
        SELECT ?, ?, ?, ?, ?
        ON CONFLICT (employee_id) DO NOTHING
    """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                Date hireDate = resultSet.getDate("hire_date");
                double grade = resultSet.getDouble("grade");

                // Добавляем в список
                EmployeeGrade employeeGrade = new EmployeeGrade(id, name, department, hireDate, grade);
                grades.add(employeeGrade);

                // Вставляем данные в таблицу employee_grades
                insertStatement.setInt(1, id);
                insertStatement.setString(2, name);
                insertStatement.setString(3, department);
                insertStatement.setDate(4, new java.sql.Date(hireDate.getTime()));
                insertStatement.setDouble(5, grade);
                insertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }


    public void addGrade(int employeeId, double grade) {
        String query = "INSERT INTO grades (employee_id, grade) "
                + "VALUES (?, ?) "
                + "ON CONFLICT (employee_id) DO UPDATE "
                + "SET grade = EXCLUDED.grade";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.setDouble(2, grade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean gradeExists(int id) {
        String query = "SELECT COUNT(*) FROM grades WHERE employee_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Если количество записей больше 0, значит оценка существует
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Возвращаем false, если оценки не существует
    }

    public void updateGrade(int employeeId, double grade) {
        String query = "UPDATE grades SET grade = ? WHERE employee_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, grade);
            stmt.setInt(2, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
