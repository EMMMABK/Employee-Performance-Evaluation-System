package org.example.employeeperformanceapp;

import java.util.Date;

public class PerformanceReview {
    private int id;
    private int employeeId;
    private Date reviewDate;
    private int performanceScore;

    // Конструкторы, геттеры и сеттеры
    public PerformanceReview(int id, int employeeId, Date reviewDate, int performanceScore) {
        this.id = id;
        this.employeeId = employeeId;
        this.reviewDate = reviewDate;
        this.performanceScore = performanceScore;
    }

    // геттеры и сеттеры


    public int getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(int performanceScore) {
        this.performanceScore = performanceScore;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Review ID: " + id +
                ", Employee ID: " + employeeId +
                ", Date: " + reviewDate +
                ", Score: " + performanceScore;
    }
}