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
    private TableView<Employee> gradeTable;
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
            showAlert("Информация", "Сотрудник успешно добавлен в список!!!");
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
            showAlert("Информация", "Сотрудник добавлен в корзину!!!");
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
            System.out.println("Сотрудник восстановлен!!!");
            showAlert("Информация", "Сотрудник восстановлен!!!");
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

    public void loadGradeData() {
        List<EmployeeGrade> grades = employeeDAO.getAllGrades();
        gradeTable.getItems().setAll(grades);
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
//        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
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

                // Проверка, существует ли оценка для данного сотрудника
                if (!employeeDAO.gradeExists(selectedEmployee.getId())) {
                    // Если оценки нет, добавляем ее
                    employeeDAO.addGrade(selectedEmployee.getId(), averageScore);
                } else {
                    // Если оценка уже есть, обновляем ее
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

}
