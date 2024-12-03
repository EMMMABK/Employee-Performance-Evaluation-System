package org.example.employeeperformanceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
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

    public void initialize() {
        // Подключаемся к базе данных
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "190306");
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
    public void evaluateEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                // Replace these with actual inputs (e.g., from TextFields or Dialogs)
                double attendance = Double.parseDouble("Enter attendance score (0-100):");
                double hardSkills = Double.parseDouble("Enter hard skills score (0-100):");
                double softSkills = Double.parseDouble("Enter soft skills score (0-100):");

                if (attendance < 0 || attendance > 100 || hardSkills < 0 || hardSkills > 100 || softSkills < 0 || softSkills > 100) {
                    showAlert("Input Error", "Scores must be between 0 and 100.");
                    return;
                }

                double averageScore = (attendance + hardSkills + softSkills) / 3;
                showAlert("Evaluation Result", "The average score is: " + averageScore);
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numerical values.");
            }
        } else {
            showAlert("Selection Error", "Please select an employee to evaluate.");
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