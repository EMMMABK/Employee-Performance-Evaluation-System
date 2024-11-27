package org.example.employeeperformanceevaluationsystem;

public class Employee {
    private final String name;
    private final String department;
    private final int performanceScore;

    public Employee(String name, String department, int performanceScore) {
        this.name = name;
        this.department = department;
        this.performanceScore = performanceScore;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }
}
