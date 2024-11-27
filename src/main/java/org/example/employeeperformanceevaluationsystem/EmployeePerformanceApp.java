package org.example.employeeperformanceevaluationsystem;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePerformanceApp extends Application {

    private List<Employee> employeeList = new ArrayList<>();
    private VBox employeeDisplayArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Performance Evaluation System");

        // Main Layout
        BorderPane mainLayout = new BorderPane();

        // Input Form
        VBox inputForm = createInputForm();

        // Employee Display Area
        employeeDisplayArea = new VBox();
        employeeDisplayArea.setSpacing(10);
        ScrollPane scrollPane = new ScrollPane(employeeDisplayArea);
        scrollPane.setFitToWidth(true);

        mainLayout.setLeft(inputForm);
        mainLayout.setCenter(scrollPane);

        // Scene
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createInputForm() {
        VBox form = new VBox();
        form.setSpacing(10);
        form.setStyle("-fx-padding: 20; -fx-border-color: gray; -fx-border-width: 2;");

        // Input Fields
        TextField nameField = new TextField();
        nameField.setPromptText("Employee Name");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");

        TextField performanceScoreField = new TextField();
        performanceScoreField.setPromptText("Performance Score (1-100)");

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> {
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

                Employee employee = new Employee(name, department, score);
                employeeList.add(employee);
                addEmployeeToDisplay(employee);

                nameField.clear();
                departmentField.clear();
                performanceScoreField.clear();

            } catch (NumberFormatException ex) {
                showAlert("Error", "Performance score must be a number.");
            }
        });

        form.getChildren().addAll(
                new Label("Add New Employee"),
                nameField,
                departmentField,
                performanceScoreField,
                addButton
        );

        return form;
    }

    private void addEmployeeToDisplay(Employee employee) {
        HBox employeeBox = new HBox();
        employeeBox.setSpacing(10);
        employeeBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");

        Label nameLabel = new Label("Name: " + employee.getName());
        Label departmentLabel = new Label("Department: " + employee.getDepartment());
        Label scoreLabel = new Label("Score: " + employee.getPerformanceScore());

        employeeBox.getChildren().addAll(nameLabel, departmentLabel, scoreLabel);
        employeeDisplayArea.getChildren().add(employeeBox);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


