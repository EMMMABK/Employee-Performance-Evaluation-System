package org.example.employeeperformanceapp;

import javafx.beans.property.SimpleDoubleProperty;
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
    private TableView<Employee> trashTable;
    @FXML
    private TableView<Employee> gradeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> nameColumnTrash;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumnTrash;
    @FXML
    private TextField nameField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField attendance;
    @FXML
    private TextField sofskill;
    @FXML
    private TextField hardskill;
    @FXML
    private TableColumn<EmployeeGrade, String> nameColumnGrade;
    @FXML
    private TableColumn<EmployeeGrade, String> departmentColumnGrade;
    @FXML
    private TableColumn<EmployeeGrade, Double> gradeColumn;

    @FXML
    private EmployeeDAO employeeDAO;

    public void initialize() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
            employeeDAO = new EmployeeDAO(connection);

            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            nameColumnTrash.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            departmentColumnTrash.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            nameColumnGrade.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            departmentColumnGrade.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            gradeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getGrade()).asObject());

            loadGradeData();
            loadEmployeeData();
            loadTrashData();
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
        trashTable.getItems().setAll(employees);
    }
    public void loadGradeData() {
        List<EmployeeGrade> grades = employeeDAO.getAllGrades();
        gradeTable.getItems().setAll(grades);
    }
    public void clearFields(){
        nameField.clear();
        departmentField.clear();
        sofskill.clear();
        hardskill.clear();
        attendance.clear();
    }
    @FXML
    public void addEmployee() {
        String name = nameField.getText();
        String department = departmentField.getText();
        if (!name.isEmpty() && !department.isEmpty()) {
            Employee employee = new Employee(0, name, department, new java.util.Date());
            employeeDAO.addEmployee(employee);
            loadEmployeeData();
            loadGradeData();
            clearFields();
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
            loadTrashData();
            loadGradeData();
            showAlert("Information", "The employee has been added to your trash.");
        } else {
            showAlert("Selection Error", "Please select an employee to delete.");
        }
    }

    @FXML
    public void restoreEmployee() {
        Employee selectedEmployee = trashTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                employeeDAO.restoreFromTrash(selectedEmployee.getId());
                loadTrashData();
                loadEmployeeData();
                loadGradeData();
                showAlert("Information", "The employee has been restored!");
            } catch (SQLException e) {
                if ("An employee with this ID already exists!".equals(e.getMessage())) {
                    showAlert("Error", "An employee with this ID already exists!");
                } else {
                    showAlert("Error", "An error occurred while restoring the employee: " + e.getMessage());
                }
            }
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
                loadGradeData();
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
        Employee selectedEmployee = gradeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                double attendance = Double.parseDouble(this.attendance.getText());
                double hardSkill = Double.parseDouble(this.hardskill.getText());
                double softSkill = Double.parseDouble(this.sofskill.getText());

                if (attendance < 0 || attendance > 10 || hardSkill < 0 || hardSkill > 10 || softSkill < 0 || softSkill > 10) {
                    showAlert("Input Error", "Scores must be between 0 and 10.");
                    return;
                }

                double averageScore = (attendance + hardSkill + softSkill) / 3;

                if (!employeeDAO.gradeExists(selectedEmployee.getId())) {
                    employeeDAO.addGrade(selectedEmployee.getId(), averageScore);
                } else {
                    employeeDAO.updateGrade(selectedEmployee.getId(), averageScore);
                }

                loadGradeData();
                clearFields();
                showAlert("Evaluation Result", "The employee's grade is: " + averageScore);
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numerical values.");
            }
        } else {
            showAlert("Selection Error", "Please select an employee to evaluate.");
        }
    }



    @FXML
    public void deleteEmployee() {
        Employee selectedEmployee = trashTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.deleteEmployee(selectedEmployee.getId());
            loadTrashData();
            loadGradeData();
            showAlert("Information", "The employee has been permanently deleted.");
        } else {
            showAlert("Selection Error", "Please select an employee to delete.");
        }
    }

}
