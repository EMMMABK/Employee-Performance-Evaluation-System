package org.example.employeeperformanceevaluationsystem;

public class Evaluation {
    private final Employee employee;
    private final double projectScore;
    private final double colleagueScore;
    private final double attendanceScore;

    public Evaluation(Employee employee, double projectScore, double colleagueScore, double attendanceScore) {
        this.employee = employee;
        this.projectScore = projectScore;
        this.colleagueScore = colleagueScore;
        this.attendanceScore = attendanceScore;
    }

    public double calculateAverage() {
        return (projectScore + colleagueScore + attendanceScore) / 3;
    }

    // Геттеры и сеттеры
    public Employee getEmployee() {
        return employee;
    }

    public double getProjectScore() {
        return projectScore;
    }

    public double getColleagueScore() {
        return colleagueScore;
    }

    public double getAttendanceScore() {
        return attendanceScore;
    }
}
