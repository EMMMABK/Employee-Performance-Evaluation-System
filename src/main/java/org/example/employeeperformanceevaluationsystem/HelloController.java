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
        editButton.setOnAction(e -> editSelectedEmployee());
        evaluateButton.setOnAction(e -> evaluateSelectedEmployee());
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

            // Здесь добавьте логику для сохранения данных оценки
            evaluateGridPane.setVisible(false);
        });
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
