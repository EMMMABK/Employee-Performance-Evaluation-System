package org.example.employeeperformanceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HelloController {
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField departmentField;

    @FXML

    private EmployeeDAO employeeDAO;
    @FXML
    private TextField attendanceField;
    @FXML
    private TextField hardSkillsField;
    @FXML
    private TextField softSkillsField;
    private Connection connection;

    public void initialize() {
        // Подключаемся к базе данных
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
            employeeDAO = new EmployeeDAO(connection);

            // Настроить таблицу
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());

            loadEmployeeData();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the database.");
        }
    }

    public void loadEmployeeData() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employeeTable.getItems().setAll(employees);
    }

    public void loadTrashData() {
        List<Employee> employees = employeeDAO.getAllFromTrash();
        employeeTable.getItems().setAll(employees);
    }
    public void clearFields(){
        nameField.clear();
        departmentField.clear();
    }
    @FXML
    public void addEmployee() {
        String name = nameField.getText();
        String department = departmentField.getText();
        if (!name.isEmpty() && !department.isEmpty()) {
            Employee employee = new Employee(0, name, department, new java.util.Date());
            employeeDAO.addEmployee(employee);
            loadEmployeeData();
            // Очищаем поля после добавления
            clearFields();
            // Создаем кнопку или другое событие, чтобы вызвать alert
            showAlert("Information", "The employee has been added to the list.");
        } else {
            showAlert("Input Error", "Please fill all fields.");
        }
    }

    @FXML
    public void moveToTrash() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.moveToTrash(selectedEmployee.getId());
            loadEmployeeData();
            showAlert("Information", "The employee has been added to your trash.");
        } else {
            showAlert("Selection Error", "Please select an employee to delete.");
        }
    }

    @FXML
    public void restoreEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.restoreFromTrash((selectedEmployee.getId()));
            loadTrashData();
            showAlert("Information", "The employee has been restored!");
        } else {
            showAlert("Selection Error", "Please select an employee to restore.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void editEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            String name = nameField.getText();
            String department = departmentField.getText();

            if (!name.isEmpty() && !department.isEmpty()) {
                selectedEmployee.setName(name);
                selectedEmployee.setDepartment(department);
                employeeDAO.updateEmployee(selectedEmployee);
                loadEmployeeData();
                clearFields();
                showAlert("Information", "Employee details have been updated.");
            } else {
                showAlert("Input Error", "Please fill all fields.");
            }
        } else {
            showAlert("Selection Error", "Please select an employee to edit.");
        }
    }

    @FXML
    public void evaluationEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                // Получаем значения из полей ввода
                int attendance = Integer.parseInt(attendanceField.getText());
                int hardSkills = Integer.parseInt(hardSkillsField.getText());
                int softSkills = Integer.parseInt(softSkillsField.getText());

                // Проверяем, чтобы все оценки были в пределах 0-10
                if (attendance >= 0 && attendance <= 10 && hardSkills >= 0 && hardSkills <= 10 && softSkills >= 0 && softSkills <= 10) {
                    // Расчет среднего балла
                    double averageScore = (attendance + hardSkills + softSkills) / 3.0;

                    // Обновляем данные сотрудника в базе данных
                    updateGrade(selectedEmployee.getId(), attendance, hardSkills, softSkills, averageScore);

                    // Обновляем таблицу
                    loadEmployeeData();
                    showAlert("Success", "The evaluation has been successfully updated.");
                    clearFields();
                } else {
                    showAlert("Input Error", "Please enter values between 0 and 10.");
                }
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numbers for all fields.");
            }
        } else {
            showAlert("Selection Error", "Please select an employee to evaluate.");
        }
    }

    private void updateGrade(int employeeId, int attendance, int hardSkills, int softSkills, double averageScore) {
        if (connection == null) {
            showAlert("Database Error", "Connection is not established.");
            return;
        }

        String query = "UPDATE grades SET attendance = ?, hard_skills = ?, soft_skills = ?, average_score = ? WHERE employee_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, attendance);
            stmt.setInt(2, hardSkills);
            stmt.setInt(3, softSkills);
            stmt.setDouble(4, averageScore);
            stmt.setInt(5, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not update the performance evaluation.");
        }
    }

    @FXML
    public void deleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.deleteEmployee(selectedEmployee.getId());
            loadEmployeeData();
            showAlert("Information", "The employee has been permanently deleted.");
        } else {
            showAlert("Selection Error", "Please select an employee to delete.");
        }
    }

}
