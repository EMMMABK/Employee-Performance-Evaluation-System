package org.example.employeeperformanceevaluationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField performanceScoreField;

    @FXML
    private VBox employeeDisplayArea;

    @FXML
    private void onAddEmployeeButtonClick() {
        String name = nameField.getText();
        String department = departmentField.getText();
        String scoreText = performanceScoreField.getText();

        if (name.isEmpty() || department.isEmpty() || scoreText.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        try {
            int score = Integer.parseInt(scoreText);
            if (score < 1 || score > 100) {
                showAlert("Error", "Performance score must be between 1 and 100.");
                return;
            }

            Label employeeInfo = new Label("Name: " + name + ", Department: " + department + ", Score: " + score);
            employeeInfo.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");
            employeeDisplayArea.getChildren().add(employeeInfo);

            // Clear fields
            nameField.clear();
            departmentField.clear();
            performanceScoreField.clear();

        } catch (NumberFormatException ex) {
            showAlert("Error", "Performance score must be a number.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
