package org.example.employeeperformanceevaluationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HelloController {
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab employeeTab, marksTab, evaluationTab;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private Button addButton, editButton, deleteButton, evaluateButton;

    @FXML
    private VBox evaluationCriteriaBox;

    @FXML
    private Label evaluationLabel;

    @FXML
    private Slider projectCompletionSlider, teamFeedbackSlider, attendanceSlider;

    @FXML
    public void initialize() {
        setupEmployeeTab();
        setupMarksTab();
        setupEvaluationTab();
    }

    private void setupEmployeeTab() {
        addButton.setOnAction(event -> {
            // Реализация функции добавления сотрудника
            System.out.println("Добавить сотрудника");
        });

        editButton.setOnAction(event -> {
            // Реализация функции редактирования сотрудника
            System.out.println("Редактировать сотрудника");
        });

        deleteButton.setOnAction(event -> {
            // Реализация функции удаления сотрудника
            System.out.println("Удалить сотрудника");
        });
    }

    private void setupMarksTab() {
        evaluateButton.setOnAction(event -> {
            // Открытие вкладки оценки
            tabPane.getSelectionModel().select(evaluationTab);
        });
    }

    private void setupEvaluationTab() {
        // Сохранение данных после оценки
        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(event -> {
            double projectScore = projectCompletionSlider.getValue();
            double teamFeedbackScore = teamFeedbackSlider.getValue();
            double attendanceScore = attendanceSlider.getValue();
            System.out.println("Оценки сохранены: ");
            System.out.println("Сдача проектов: " + projectScore);
            System.out.println("Мнение сокомандников: " + teamFeedbackScore);
            System.out.println("Посещаемость: " + attendanceScore);
            // Реализация сохранения данных
        });
        evaluationCriteriaBox.getChildren().add(saveButton);
    }
}
