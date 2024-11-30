package org.example.employeeperformanceevaluationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class HelloController {

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> departmentColumn;

    @FXML
    private TableColumn<Employee, String> evaluationColumn;

    @FXML
    private Button removeButton;

    @FXML
    private Button addButton;

    @FXML
    private GridPane editGridPane;

    @FXML
    private GridPane evaluateGridPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField deptField;

    @FXML
    private TextField evaluationField;

    @FXML
    private TextField hardSkillsField;

    @FXML
    private TextField softSkillsField;

    @FXML
    private TextField attendanceField;

    @FXML
    private Button saveEditButton;

    @FXML
    private Button saveEvaluateButton;

    @FXML
    private Button editButton;

    @FXML
    private Button evaluateButton;

    public void initialize() {
        addButton.setOnAction(e -> addNewEmployee());
        removeButton.setOnAction(e -> removeSelectedEmployee());
        editButton.setOnAction(e -> editSelectedEmployee());
        evaluateButton.setOnAction(e -> evaluateSelectedEmployee());
    }

    private void addNewEmployee() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter new employee details");
        dialog.setContentText("Format: FullName, Department, Evaluation (0-10)");

        dialog.showAndWait().ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length != 3) {
                showAlert("Invalid format! Use: FullName, Department, Evaluation", Alert.AlertType.WARNING);
                return;
            }

            try {
                String fullName = parts[0].trim();
                String department = parts[1].trim();
                double evaluation = Double.parseDouble(parts[2].trim());

                if (evaluation < 0 || evaluation > 10) {
                    throw new NumberFormatException("Evaluation out of range");
                }

                // Генерация уникального ID для нового сотрудника
                int newId = generateNewEmployeeId();

                Employee newEmployee = new Employee(newId, fullName, department, String.valueOf(evaluation));
                tableView.getItems().add(newEmployee);
                showAlert("Employee added successfully!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                showAlert("Evaluation must be a number between 0 and 10!", Alert.AlertType.WARNING);
            }
        });
    }

    private int generateNewEmployeeId() {
        // Логика для генерации уникального ID
        // Пример генерации ID, можно заменить на более сложный механизм, например, инкремент.
        return (int) (Math.random() * 10000);  // Простой пример
    }


    private void removeSelectedEmployee() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No employee selected!", Alert.AlertType.WARNING);
            return;
        }

        tableView.getItems().remove(selected);
        showAlert("Employee removed successfully!", Alert.AlertType.INFORMATION);
    }

    private void editSelectedEmployee() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No employee selected!", Alert.AlertType.WARNING);
            return;
        }

        editGridPane.setVisible(true);
        evaluateGridPane.setVisible(false);

        nameField.setText(selected.getFullName());
        deptField.setText(selected.getDepartment());
        evaluationField.setText(selected.getEvaluation());

        saveEditButton.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            String department = deptField.getText().trim();
            String evaluationInput = evaluationField.getText().trim();

            if (fullName.isEmpty() || department.isEmpty() || evaluationInput.isEmpty()) {
                showAlert("All fields must be filled!", Alert.AlertType.WARNING);
                return;
            }

            try {
                double evaluation = Double.parseDouble(evaluationInput);
                if (evaluation < 0 || evaluation > 10) {
                    throw new NumberFormatException("Evaluation out of range");
                }

                selected.setFullName(fullName);
                selected.setDepartment(department);
                selected.setEvaluation(String.valueOf(evaluation));
                tableView.refresh();
                editGridPane.setVisible(false);
            } catch (NumberFormatException ex) {
                showAlert("Evaluation must be a number between 0 and 10!", Alert.AlertType.WARNING);
            }
        });
    }

    private void evaluateSelectedEmployee() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No employee selected!", Alert.AlertType.WARNING);
            return;
        }

        editGridPane.setVisible(false);
        evaluateGridPane.setVisible(true);

        saveEvaluateButton.setOnAction(e -> {
            String hardSkills = hardSkillsField.getText().trim();
            String softSkills = softSkillsField.getText().trim();
            String attendance = attendanceField.getText().trim();

            if (hardSkills.isEmpty() || softSkills.isEmpty() || attendance.isEmpty()) {
                showAlert("All fields must be filled!", Alert.AlertType.WARNING);
                return;
            }

            String evaluationDetails = String.format("Hard: %s, Soft: %s, Attendance: %s", hardSkills, softSkills, attendance);
            selected.setEvaluation(evaluationDetails);
            tableView.refresh();
            evaluateGridPane.setVisible(false);
            showAlert("Evaluation saved successfully!", Alert.AlertType.INFORMATION);
        });
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}

