package org.example.employeeperformanceevaluationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class HelloController {
    @FXML
    private Tab employeeTab;
    @FXML
    private Tab evaluationTab;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> idColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    private TableView<Evaluation> evaluationTable;
    @FXML
    private TableColumn<Evaluation, String> employeeColumn;
    @FXML
    private TableColumn<Evaluation, Double> avgScoreColumn;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button evaluateButton;

    private EmployeeManager employeeManager = new EmployeeManager();

    @FXML
    public void initialize() {
        // Инициализация таблиц
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));

        employeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFullName()));
        avgScoreColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calculateAverage()).asObject());

        // Слушатели для кнопок
        addEmployeeButton.setOnAction(event -> addEmployee());
        evaluateButton.setOnAction(event -> evaluateEmployee());

        // Заполнение начальных данных
        updateEmployeeTable();
    }

    // Добавление сотрудника
    public void addEmployee() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Employee");
        dialog.setHeaderText("Enter Full Name and Department (comma separated):");
        dialog.showAndWait().ifPresent(input -> {
            String[] data = input.split(",");
            if (data.length == 2) {
                Employee newEmployee = new Employee(data[0], data[1]);
                employeeManager.addEmployee(newEmployee);
                updateEmployeeTable();
            }
        });
    }

    // Обновление таблицы сотрудников
    private void updateEmployeeTable() {
        employeeTable.getItems().setAll(employeeManager.getEmployees());
    }

    // Обновление таблицы оценок
    private void updateEvaluationTable() {
        evaluationTable.getItems().setAll(employeeManager.getEvaluations());
    }

    // Оценка сотрудника
    public void evaluateEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null && !selectedEmployee.isEvaluated()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Evaluate Employee");
            dialog.setHeaderText("Enter Scores (Project, Colleague, Attendance):");
            dialog.showAndWait().ifPresent(input -> {
                String[] scores = input.split(",");
                if (scores.length == 3) {
                    try {
                        double projectScore = Double.parseDouble(scores[0].trim());
                        double colleagueScore = Double.parseDouble(scores[1].trim());
                        double attendanceScore = Double.parseDouble(scores[2].trim());
                        employeeManager.evaluateEmployee(selectedEmployee, projectScore, colleagueScore, attendanceScore);
                        updateEmployeeTable();
                        updateEvaluationTable();
                    } catch (NumberFormatException e) {
                        showErrorDialog("Invalid scores entered!");
                    }
                } else {
                    showErrorDialog("Please enter three scores.");
                }
            });
        } else {
            showErrorDialog("Please select an employee or the employee has already been evaluated.");
        }
    }

    // Ошибка валидации
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Удаление сотрудника
    @FXML
    private void removeEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeManager.removeEmployee(selectedEmployee);
            updateEmployeeTable();
        }
    }

    // Редактирование сотрудника
    @FXML
    private void editEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Employee");
            dialog.setHeaderText("Edit Employee's Full Name and Department:");
            dialog.showAndWait().ifPresent(input -> {
                String[] data = input.split(",");
                if (data.length == 2) {
                    employeeManager.editEmployee(selectedEmployee, data[0], data[1]);
                    updateEmployeeTable();
                }
            });
        }
    }
}
