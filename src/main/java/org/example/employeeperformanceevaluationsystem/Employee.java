package org.example.employeeperformanceevaluationsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty department;
    private final SimpleStringProperty evaluation;

    public Employee(int id, String fullName, String department, String evaluation) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.department = new SimpleStringProperty(department);
        this.evaluation = new SimpleStringProperty(evaluation == null ? "" : evaluation);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getDepartment() {
        return department.get();
    }

    public String getEvaluation() {
        return evaluation.get();
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public void setEvaluation(String evaluation) {
        this.evaluation.set(evaluation);
    }

    // Properties (for TableView binding)
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public SimpleStringProperty evaluationProperty() {
        return evaluation;
    }
}
