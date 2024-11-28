package org.example.employeeperformanceevaluationsystem;

import javafx.beans.property.*;

public class Employee {
    private final IntegerProperty id;
    private final StringProperty fullName;
    private final StringProperty department;
    private final StringProperty evaluation;

    public Employee(int id, String fullName, String department, String evaluation) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.department = new SimpleStringProperty(department);
        this.evaluation = new SimpleStringProperty(evaluation);
    }

    // Геттеры и сеттеры для ID
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Геттеры и сеттеры для FullName
    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    // Геттеры и сеттеры для Department
    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public StringProperty departmentProperty() {
        return department;
    }

    // Геттеры и сеттеры для Evaluation
    public String getEvaluation() {
        return evaluation.get();
    }

    public void setEvaluation(String evaluation) {
        this.evaluation.set(evaluation);
    }

    public StringProperty evaluationProperty() {
        return evaluation;
    }
}
