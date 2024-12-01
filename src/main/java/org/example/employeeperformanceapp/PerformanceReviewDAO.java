package org.example.employeeperformanceapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewDAO implements PerformanceReviewDAOInterface {
    private Connection connection;

    public PerformanceReviewDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPerformanceReview(PerformanceReview review) {
        String query = "INSERT INTO performance_reviews (employee_id, review_date, performance_score) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getEmployeeId());
            statement.setDate(2, new java.sql.Date(review.getReviewDate().getTime()));
            statement.setInt(3, review.getPerformanceScore());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PerformanceReview> getReviewsByEmployee(int employeeId) {
        List<PerformanceReview> reviews = new ArrayList<>();
        String query = "SELECT * FROM performance_reviews WHERE employee_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PerformanceReview review = new PerformanceReview(
                        resultSet.getInt("id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getDate("review_date"),
                        resultSet.getInt("performance_score")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
