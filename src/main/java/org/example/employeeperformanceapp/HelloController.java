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

    @FXML
    public void addEmployee() {
        String name = nameField.getText();
        String department = departmentField.getText();
        if (!name.isEmpty() && !department.isEmpty()) {
            Employee employee = new Employee(0, name, department, new java.util.Date());
            employeeDAO.addEmployee(employee);
            loadEmployeeData();
        } else {
            showAlert("Input Error", "Please fill all fields.");
        }
    }

    @FXML
    public void deleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.moveToTrash(selectedEmployee.getId());
            loadEmployeeData();
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
            System.out.println("Сотрудник восстановлен");
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
}
