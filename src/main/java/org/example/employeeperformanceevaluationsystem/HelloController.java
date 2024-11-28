package org.example.employeeperformanceevaluationsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableView<Employee> marksTableView;

    @FXML
    private Button addButton, editButton, deleteButton;

    @FXML
    private Tab evaluationTab;

    @FXML
    private VBox evaluationCriteriaBox;

    @FXML
    private Slider projectCompletionSlider, teamFeedbackSlider, attendanceSlider;

    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() {
        setupTabs();
        setupEmployeeTable();
        setupEmployeeTabButtons();
        setupEvaluationTab();
    }

    private void setupTabs() {
        evaluationTab.setClosable(false); // Запрет закрытия Evaluation
    }

    private void setupEmployeeTable() {
        employeeList = FXCollections.observableArrayList(
                new Employee(1, "Иван Иванов", "IT", 0),
                new Employee(2, "Мария Смирнова", "HR", 0),
                new Employee(3, "Алексей Кузнецов", "Marketing", 0)
        );

        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Employee, String> fullnameColumn = new TableColumn<>("Полное имя");
        fullnameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullname()));

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Отдел");
        departmentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment()));

        TableColumn<Employee, Integer> evaluationColumn = new TableColumn<>("Оценка");
        evaluationColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEvaluation()).asObject());

        employeeTableView.getColumns().addAll(idColumn, fullnameColumn, departmentColumn, evaluationColumn);
        marksTableView.getColumns().addAll(idColumn, fullnameColumn, departmentColumn, evaluationColumn);

        employeeTableView.setItems(employeeList);
        marksTableView.setItems(employeeList);
    }

    private void setupEmployeeTabButtons() {
        addButton.setOnAction(event -> addEmployee());
        editButton.setOnAction(event -> editEmployee());
        deleteButton.setOnAction(event -> deleteEmployee());
    }

    private void addEmployee() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавить сотрудника");
        dialog.setHeaderText("Введите имя нового сотрудника:");
        dialog.showAndWait().ifPresent(name -> {
            int newId = employeeList.size() + 1;
            employeeList.add(new Employee(newId, name, "Новый отдел", 0));
        });
    }

    private void editEmployee() {
        Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите сотрудника для редактирования.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getFullname());
        dialog.setTitle("Редактировать сотрудника");
        dialog.setHeaderText("Измените имя:");
        dialog.showAndWait().ifPresent(newName -> selected.setFullname(newName));
        employeeTableView.refresh();
    }

    private void deleteEmployee() {
        Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите сотрудника для удаления.");
            return;
        }
        employeeList.remove(selected);
    }

    private void setupEvaluationTab() {
        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(event -> {
            Employee selected = marksTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Ошибка", "Выберите сотрудника для оценки.");
                return;
            }

            int projectScore = (int) projectCompletionSlider.getValue();
            int teamScore = (int) teamFeedbackSlider.getValue();
            int attendanceScore = (int) attendanceSlider.getValue();

            int totalEvaluation = (projectScore + teamScore + attendanceScore) / 3;
            selected.setEvaluation(totalEvaluation);

            marksTableView.refresh();
            employeeTableView.refresh();
        });
        evaluationCriteriaBox.getChildren().add(saveButton);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
