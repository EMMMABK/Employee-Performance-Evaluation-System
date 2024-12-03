package org.example.employeeperformanceapp;

import java.util.Date;

public class PerformanceReview {
    private int id;
    private int employeeId;
    private Date reviewDate;
    private int performanceScore;
    private double attendance;  // Add attendance field
    private double hardSkills;  // Add hard skills field
    private double softSkills;  // Add soft skills field

    // Constructor with new fields
    public PerformanceReview(int id, int employeeId, Date reviewDate, int performanceScore, double attendance, double hardSkills, double softSkills) {
        this.id = id;
        this.employeeId = employeeId;
        this.reviewDate = reviewDate;
        this.performanceScore = performanceScore;
        this.attendance = attendance;
        this.hardSkills = hardSkills;
        this.softSkills = softSkills;
    }

    // Getters and Setters for the new fields
    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public double getHardSkills() {
        return hardSkills;
    }

    public void setHardSkills(double hardSkills) {
        this.hardSkills = hardSkills;
    }

    public double getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(double softSkills) {
        this.softSkills = softSkills;
    }

    // Existing getters and setters
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
                ", Score: " + performanceScore +
                ", Attendance: " + attendance +
                ", Hard Skills: " + hardSkills +
                ", Soft Skills: " + softSkills;
    }
}
