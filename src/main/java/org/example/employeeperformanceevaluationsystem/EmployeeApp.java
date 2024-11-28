package org.example.employeeperformanceevaluationsystem;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EmployeeApp extends Application {

    private final TableView<Employee> table = new TableView<>();
    private final ObservableList<Employee> employees = FXCollections.observableArrayList(
            new Employee(1, "John Doe", "IT", ""),
            new Employee(2, "Jane Smith", "HR", ""),
            new Employee(3, "Tom Brown", "Finance", "")
    );

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management");

        // Таблица сотрудников
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Full Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());

        TableColumn<Employee, String> deptColumn = new TableColumn<>("Department");
        deptColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());

        TableColumn<Employee, String> evalColumn = new TableColumn<>("Evaluation");
        evalColumn.setCellValueFactory(cellData -> cellData.getValue().evaluationProperty());

        table.setItems(employees);
        table.getColumns().addAll(idColumn, nameColumn, deptColumn, evalColumn);

        // Кнопки управления
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeSelectedEmployee());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editSelectedEmployee());

        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setOnAction(e -> evaluateSelectedEmployee(primaryStage));

        buttonBox.getChildren().addAll(removeButton, editButton, evaluateButton);

        VBox layout = new VBox(10, table, buttonBox);
        layout.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(layout, 600, 400));
        primaryStage.show();
    }

    private void removeSelectedEmployee() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employees.remove(selected);
        }
    }

    private void editSelectedEmployee() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Edit feature not implemented yet!");
        alert.showAndWait();
    }

    private void evaluateSelectedEmployee(Stage parentStage) {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            new EvaluationForm(selected).showAndWait();
            table.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No employee selected!");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
