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
//        Using Overloading Method for add function
        addNewEmployee(999, "Admin Adminov", "System Administrator", "10.0");
        addNewEmployee(998, "Test Tester", "Tester QA", "10.0");

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeSelectedEmployee());
//        Using Overloading Method  for remove function
        removeSelectedEmployee(998);  // Удаляет сотрудника с ID 2


        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editSelectedEmployee());
//        Using Overloading Method for edit function
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            editSelectedEmployee(selected, "Edit Overload", "Overloading Department", "9.0");
        }

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

        Label jobTitleLabel = new Label("Job Title:");
        TextField jobTitleField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            String department = deptField.getText().trim();
            String jobTitle = jobTitleField.getText().trim();

            if (!fullName.isEmpty() && !department.isEmpty() && !jobTitle.isEmpty()) {
                int newId = employees.size() + 1; // Simple way to generate ID
                employees.add(new Employee(newId, fullName, department, jobTitle));
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
        grid.add(jobTitleLabel, 0, 2);
        grid.add(jobTitleField, 1, 2);
        grid.add(saveButton, 1, 3);

        addStage.setScene(new Scene(grid, 300, 250));
        addStage.showAndWait();
    }

//    Overloading Method for add function
    private void addNewEmployee(int id, String fullName, String department, String evaluation) {
        if (!fullName.isEmpty() && !department.isEmpty()) {
            employees.add(new Employee(id, fullName, department, evaluation));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled!");
            alert.showAndWait();
        }
    }



    private void removeSelectedEmployee() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employees.remove(selected);
        }
    }

//    Overloading method for Remove Function
    private void removeSelectedEmployee(int id) {
    Employee toRemove = employees.stream()
            .filter(employee -> employee.getId() == id)
            .findFirst()
            .orElse(null);

    if (toRemove != null) {
        employees.remove(toRemove);
    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Employee not found!");
        alert.showAndWait();
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

        Label evaluationLabel = new Label("Evaluation (0-10):");
        TextField evaluationField = new TextField(selected.getEvaluation());
        evaluationField.setPromptText("Enter a number between 0 and 10");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            String department = deptField.getText().trim();
            String evaluationInput = evaluationField.getText().trim();

            if (!fullName.isEmpty() && !department.isEmpty() && !evaluationInput.isEmpty()) {
                try {
                    double evaluation = Double.parseDouble(evaluationInput);
                    if (evaluation < 0 || evaluation > 10) {
                        throw new NumberFormatException("Evaluation out of range");
                    }

                    selected.setFullName(fullName);
                    selected.setDepartment(department);
                    selected.setEvaluation(String.valueOf(evaluation));
                    table.refresh(); // Обновляем таблицу
                    editStage.close();
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Evaluation must be a number between 0 and 10!");
                    alert.showAndWait();
                }
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
        grid.add(evaluationLabel, 0, 2);
        grid.add(evaluationField, 1, 2);
        grid.add(saveButton, 1, 3);

        editStage.setScene(new Scene(grid, 350, 250));
        editStage.showAndWait();
    }
//    Overloading method for Edit function

    private void editSelectedEmployee(Employee employee, String fullName, String department, String evaluation) {
        if (employee != null && !fullName.isEmpty() && !department.isEmpty() && !evaluation.isEmpty()) {
            employee.setFullName(fullName);
            employee.setDepartment(department);
            employee.setEvaluation(evaluation);
            table.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid data or no employee selected!");
            alert.showAndWait();
        }
    }




    private void evaluateSelectedEmployee(Stage primaryStage) {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No employee selected!");
            alert.showAndWait();
            return;
        }

        // Проверяем, есть ли уже оценка
        if (selected.getEvaluation() != null && !selected.getEvaluation().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "This employee has already been evaluated. Use Edit to change.");
            alert.showAndWait();
            return;
        }

        Stage evaluateStage = new Stage();
        evaluateStage.initOwner(primaryStage);
        evaluateStage.setTitle("Evaluate Employee");

        Label hardSkillsLabel = new Label("Hard Skills (0-10):");
        TextField hardSkillsField = new TextField();
        hardSkillsField.setPromptText("Enter a number between 0 and 10");

        Label softSkillsLabel = new Label("Soft Skills (0-10):");
        TextField softSkillsField = new TextField();
        softSkillsField.setPromptText("Enter a number between 0 and 10");

        Label attendanceLabel = new Label("Attendance (0-10):");
        TextField attendanceField = new TextField();
        attendanceField.setPromptText("Enter a number between 0 and 10");

        Button saveButton = new Button("Evaluate");
        saveButton.setOnAction(e -> {
            try {
                // Получаем оценки и валидируем их
                double hardSkills = validateScore(hardSkillsField.getText());
                double softSkills = validateScore(softSkillsField.getText());
                double attendance = validateScore(attendanceField.getText());

                // Считаем среднюю оценку
                double averageEvaluation = (hardSkills + softSkills + attendance) / 3;

                // Устанавливаем итоговую оценку
                selected.setEvaluation(String.format("%.2f", averageEvaluation));
                table.refresh(); // Обновляем таблицу
                evaluateStage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ex.getMessage());
                alert.showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(hardSkillsLabel, 0, 0);
        grid.add(hardSkillsField, 1, 0);
        grid.add(softSkillsLabel, 0, 1);
        grid.add(softSkillsField, 1, 1);
        grid.add(attendanceLabel, 0, 2);
        grid.add(attendanceField, 1, 2);
        grid.add(saveButton, 1, 3);

        evaluateStage.setScene(new Scene(grid, 400, 250));
        evaluateStage.showAndWait();
    }

    // Метод для валидации оценки
    private double validateScore(String input) throws NumberFormatException {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException("All fields must be filled!");
        }

        double score = Double.parseDouble(input);
        if (score < 0 || score > 10) {
            throw new NumberFormatException("Scores must be between 0 and 10!");
        }

        return score;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

