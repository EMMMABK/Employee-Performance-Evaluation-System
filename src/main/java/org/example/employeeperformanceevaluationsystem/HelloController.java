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
    private TabPane tabPane;

    @FXML
    private Tab employeeTab, marksTab, evaluationTab, addEditEmployeeTab;

    @FXML
    private TextField fullnameField, departmentField;

    @FXML
    private Button addButton, editButton, deleteButton, saveButton;

    @FXML
    private VBox evaluationCriteriaBox;

    @FXML
    private Slider projectCompletionSlider, teamFeedbackSlider, attendanceSlider;

    private ObservableList<Employee> employeeList;

    private Employee selectedEmployee = null; // Для редактирования

    @FXML
    public void initialize() {
        setupTabs();
        setupEmployeeTable();
        setupEmployeeTabButtons();
        setupEvaluationTab();
    }

    private void setupTabs() {
        evaluationTab.setClosable(false); // Запрет закрытия вкладки Evaluation
        addEditEmployeeTab.setClosable(true); // Вкладка для добавления/редактирования может закрываться
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
        addButton.setOnAction(event -> openAddEditTab(null));
        editButton.setOnAction(event -> {
            Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Ошибка", "Выберите сотрудника для редактирования.");
                return;
            }
            openAddEditTab(selected);
        });
        deleteButton.setOnAction(event -> deleteEmployee());
    }

    private void openAddEditTab(Employee employee) {
        if (employee != null) { // Редактирование
            fullnameField.setText(employee.getFullname());
            departmentField.setText(employee.getDepartment());
            selectedEmployee = employee;
        } else { // Добавление
            fullnameField.clear();
            departmentField.clear();
            selectedEmployee = null;
        }

        if (!tabPane.getTabs().contains(addEditEmployeeTab)) {
            tabPane.getTabs().add(addEditEmployeeTab);
        }
        tabPane.getSelectionModel().select(addEditEmployeeTab);
    }

    @FXML
    private void saveEmployee() {
        String fullname = fullnameField.getText().trim();
        String department = departmentField.getText().trim();

        if (fullname.isEmpty() || department.isEmpty()) {
            showAlert("Ошибка", "Все поля должны быть заполнены.");
            return;
        }

        if (selectedEmployee == null) { // Добавление
            int newId = employeeList.size() + 1;
            employeeList.add(new Employee(newId, fullname, department, 0));
        } else { // Редактирование
            selectedEmployee.setFullname(fullname);
            selectedEmployee.setDepartment(department);
            employeeTableView.refresh();
        }

        tabPane.getTabs().remove(addEditEmployeeTab);
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
