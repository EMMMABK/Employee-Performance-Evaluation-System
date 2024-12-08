package org.example.employeeperformanceapp;

import java.util.Date;

public class EmployeeGrade extends Employee {
    private double grade;

    public EmployeeGrade(int id, String name, String department, Date hireDate, double grade) {
        super(id, name, department, hireDate); // Вызов конструктора из Employee
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
