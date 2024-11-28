package org.example.employeeperformanceevaluationsystem;

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
        primaryStage.setTitle("Employee Performance Evaluation System");

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

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addNewEmployee());

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeSelectedEmployee());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editSelectedEmployee());

        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setOnAction(e -> evaluateSelectedEmployee(primaryStage));

        buttonBox.getChildren().addAll(addButton, removeButton, editButton, evaluateButton);

        VBox layout = new VBox(10, table, buttonBox);
        layout.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(layout, 600, 400));
        primaryStage.show();
    }

    private void addNewEmployee() {
        Stage addStage = new Stage();
        addStage.setTitle("Add New Employee");

        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();

        Label deptLabel = new Label("Department:");
        TextField deptField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            String department = deptField.getText().trim();

            if (!fullName.isEmpty() && !department.isEmpty()) {
                int newId = employees.size() + 1; // Простой способ генерировать ID
                employees.add(new Employee(newId, fullName, department, ""));
                addStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled!");
                alert.showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(deptLabel, 0, 1);
        grid.add(deptField, 1, 1);
        grid.add(saveButton, 1, 2);

        addStage.setScene(new Scene(grid, 300, 200));
        addStage.showAndWait();
    }

    private void removeSelectedEmployee() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employees.remove(selected);
        }
    }

    private void editSelectedEmployee() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No employee selected!");
            alert.showAndWait();
            return;
        }

        Stage editStage = new Stage();
        editStage.setTitle("Edit Employee");

        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField(selected.getFullName());

        Label deptLabel = new Label("Department:");
        TextField deptField = new TextField(selected.getDepartment());

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            String department = deptField.getText().trim();

            if (!fullName.isEmpty() && !department.isEmpty()) {
                selected.setFullName(fullName);
                selected.setDepartment(department);
                table.refresh(); // Обновляем таблицу
                editStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled!");
                alert.showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(deptLabel, 0, 1);
        grid.add(deptField, 1, 1);
        grid.add(saveButton, 1, 2);

        editStage.setScene(new Scene(grid, 300, 200));
        editStage.showAndWait();
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

