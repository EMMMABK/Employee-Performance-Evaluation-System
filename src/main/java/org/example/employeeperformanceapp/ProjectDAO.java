package org.example.employeeperformanceapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    private Connection connection;

    public ProjectDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new project
    public boolean addProject(String name, String description, Date startDate, Date endDate) {
        String query = "INSERT INTO projects (title, description, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e instanceof org.postgresql.util.PSQLException
                    && e.getMessage().contains("violates check constraint")) {
                System.err.println("Error: A CHECK constraint violation occurred in the 'projects' table. Please verify the input data.");
            } else {
                System.err.println("An error occurred while executing the SQL query: " + e.getMessage());
            }
        }
        return false;
    }


    // Edit an existing project
    public boolean editProject(int id, String name, String description, Date startDate, Date endDate) {
        String query = "UPDATE projects SET title = ?, description = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a project
    public boolean deleteProject(int id) {
        String query = "DELETE FROM projects WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all projects
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                );
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

}
