package org.example.employeeperformanceapp;

import java.util.List;

public interface PerformanceReviewDAOInterface {
    void addPerformanceReview(PerformanceReview review);
    List<PerformanceReview> getReviewsByEmployee(int employeeId);
}
