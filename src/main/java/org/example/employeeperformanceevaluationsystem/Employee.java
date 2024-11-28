package org.example.employeeperformanceevaluationsystem;

public class Employee {
    private int id;
    private String fullname;
    private String department;
    private int evaluation; // Новый атрибут

    public Employee(int id, String fullname, String department, int evaluation) {
        this.id = id;
        this.fullname = fullname;
        this.department = department;
        this.evaluation = evaluation;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
}
