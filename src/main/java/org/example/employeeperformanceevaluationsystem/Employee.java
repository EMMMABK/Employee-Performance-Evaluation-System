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

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public StringProperty evaluationProperty() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation.set(evaluation);
    }
}

