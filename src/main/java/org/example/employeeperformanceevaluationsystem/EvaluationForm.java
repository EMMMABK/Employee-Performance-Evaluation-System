package org.example.employeeperformanceevaluationsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EvaluationForm extends Stage {

    public EvaluationForm(Employee employee) {
        setTitle("Evaluate Employee: " + employee.fullNameProperty().get());
        initModality(Modality.APPLICATION_MODAL);

        // Поля оценки
        Label hardSkillsLabel = new Label("Hard Skills:");
        Slider hardSkillsSlider = createSlider();

        Label softSkillsLabel = new Label("Soft Skills:");
        Slider softSkillsSlider = createSlider();

        Label attendanceLabel = new Label("Attendance:");
        Slider attendanceSlider = createSlider();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            double hardSkills = hardSkillsSlider.getValue();
            double softSkills = softSkillsSlider.getValue();
            double attendance = attendanceSlider.getValue();

            double average = (hardSkills + softSkills + attendance) / 3;
            employee.setEvaluation(String.format("%.2f", average));

            close();
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(hardSkillsLabel, 0, 0);
        grid.add(hardSkillsSlider, 1, 0);

        grid.add(softSkillsLabel, 0, 1);
        grid.add(softSkillsSlider, 1, 1);

        grid.add(attendanceLabel, 0, 2);
        grid.add(attendanceSlider, 1, 2);

        grid.add(submitButton, 1, 3);

        setScene(new Scene(grid, 400, 300));
    }

    private Slider createSlider() {
        Slider slider = new Slider(0, 10, 5);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        return slider;
    }
}

