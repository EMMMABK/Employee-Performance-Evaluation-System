package org.example.employeeperformanceapp;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class HelloController {
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableView<Employee> trashTable;
    @FXML
    private TableView<Employee> gradeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> nameColumnTrash;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumnTrash;
    @FXML
    private TextField nameField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField attendance;
    @FXML
    private TextField sofskill;
    @FXML
    private TextField hardskill;
    @FXML
    private TableColumn<EmployeeGrade, String> nameColumnGrade;
    @FXML
    private TableColumn<EmployeeGrade, String> departmentColumnGrade;
    @FXML
    private TableColumn<EmployeeGrade, Double> gradeColumn;
    @FXML
    private VBox projectListVBox;
    @FXML
    private TextField nameFieldP;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TableView<Project> projectTable;
    @FXML
    private TableColumn<Project, String> namePColumn;
    @FXML
    private TableColumn<Project, String> descriptionColumn;
    @FXML
    private TableColumn<Project, Date> startDateColumn;
    @FXML
    private TableColumn<Project, Date> endDateColumn;

    private ProjectDAO projectDAO;
    @FXML
    private EmployeeDAO employeeDAO;

    public void initialize() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            employeeDAO = new EmployeeDAO(connection);
            projectDAO = new ProjectDAO(connection);

            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            nameColumnTrash.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            departmentColumnTrash.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            nameColumnGrade.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            departmentColumnGrade.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            gradeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getGrade()).asObject());

            namePColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            startDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate()));
            endDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndDate()));

            loadGradeData();
            loadEmployeeData();
            loadTrashData();
            loadProjects();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the database.");
        }
    }
    @FXML
    public void addProject() {
        String name = nameFieldP.getText();
        String description = descriptionField.getText();

        String startDateText = startDateField.getText();
        String endDateText = endDateField.getText();

        if (!isValidDate(startDateText)) {
            showAlert("Invalid Date", "Please enter the start date in the correct format: yyyy-MM-dd", AlertType.ERROR);
            return;
        }

        try {
            Date startDate = Date.valueOf(startDateText);

            Date endDate = null;
            if (!endDateText.isEmpty()) {
                if (!isValidDate(endDateText)) {
                    showAlert("Invalid Date", "Please enter the end date in the correct format: yyyy-MM-dd", AlertType.ERROR);
                    return;
                }
                endDate = Date.valueOf(endDateText);
            }

            boolean isAdded = projectDAO.addProject(name, description, startDate, endDate);
            if (isAdded) {
                showAlert("Project Added", "Project has been added successfully", AlertType.INFORMATION);
                clearFieldsProj();
                loadProjects();
            } else {
                showAlert("Error", "Failed to add project", AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            showAlert("Invalid Date", "Please enter a valid start date", AlertType.ERROR);
        }
    }


    @FXML
    public void editProject() {
        Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            int projectId = selectedProject.getId();
            String name = nameFieldP.getText();
            String description = descriptionField.getText();

            String startDateText = startDateField.getText();
            String endDateText = endDateField.getText();

            // Проверка формата startDate
            if (!isValidDate(startDateText)) {
                showAlert("Invalid Date", "Please enter the start date in the correct format: yyyy-MM-dd", AlertType.ERROR);
                return;
            }

            try {
                Date startDate = Date.valueOf(startDateText);

                Date endDate = null;
                if (!endDateText.isEmpty()) {
                    if (!isValidDate(endDateText)) {
                        showAlert("Invalid Date", "Please enter the end date in the correct format: yyyy-MM-dd", AlertType.ERROR);
                        return;
                    }
                    endDate = Date.valueOf(endDateText);
                }

                boolean isEdited = projectDAO.editProject(projectId, name, description, startDate, endDate);
                if (isEdited) {
                    showAlert("Project Edited", "Project has been edited successfully", AlertType.INFORMATION);
                    loadProjects();
                    clearFieldsProj();
                } else {
                    showAlert("Error", "Failed to edit project", AlertType.ERROR);
                }
            } catch (IllegalArgumentException e) {
                showAlert("Invalid Date", "Please enter a valid start date", AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a project to edit", AlertType.WARNING);
        }
    }


    private boolean isValidDate(String date) {
        try {
            // Try parsing the date using LocalDate, which will validate it properly
            LocalDate.parse(date);
            return true; // Date is valid
        } catch (DateTimeParseException e) {
            return false; // Date is invalid
        }
    }



    @FXML
    public void deleteProject() {
        Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            int projectId = selectedProject.getId(); // Assuming your Project class has an 'id' field

            boolean isDeleted = projectDAO.deleteProject(projectId);
            if (isDeleted) {
                showAlert("Project Deleted", "Project has been deleted successfully", AlertType.INFORMATION);
                loadProjects();
            } else {
                showAlert("Error", "Failed to delete project", AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a project to delete", AlertType.WARNING);
        }
    }

    private void clearFieldsProj() {
        nameFieldP.clear();
        descriptionField.clear();
        startDateField.clear();
        endDateField.clear();
    }

    public void loadProjects() {
        List<Project> projects = projectDAO.getAllProjects();
        projectTable.getItems().setAll(projects);
    }


    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void loadEmployeeData() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employeeTable.getItems().setAll(employees);
    }

    public void loadTrashData() {
        List<Employee> employees = employeeDAO.getAllFromTrash();
        trashTable.getItems().setAll(employees);
    }
    public void loadGradeData() {
        List<EmployeeGrade> grades = employeeDAO.getAllGrades();
        gradeTable.getItems().setAll(grades);
    }
    public void clearFields(){
        nameField.clear();
        departmentField.clear();
        sofskill.clear();
        hardskill.clear();
        attendance.clear();
    }
    @FXML
    public void addEmployee() {
        String name = nameField.getText();
        String department = departmentField.getText();
        if (!name.isEmpty() && !department.isEmpty()) {
            Employee employee = new Employee(0, name, department, new java.util.Date());
            employeeDAO.addEmployee(employee);
            loadEmployeeData();
            loadGradeData();
            clearFields();
            showAlert("Information", "The employee has been added to the list.");
        } else {
            showAlert("Input Error", "Please fill all fields.");
        }
    }

    @FXML
    public void moveToTrash() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.moveToTrash(selectedEmployee.getId());
            loadEmployeeData();
            loadTrashData();
            loadGradeData();
            showAlert("Information", "The employee has been added to your trash.");
        } else {
            showAlert("Selection Error", "Please select an employee to delete.");
        }
    }

    @FXML
    public void restoreEmployee() {
        Employee selectedEmployee = trashTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                employeeDAO.restoreFromTrash(selectedEmployee.getId());
                loadTrashData();
                loadEmployeeData();
                loadGradeData();
                showAlert("Information", "The employee has been restored!");
            } catch (SQLException e) {
                if ("An employee with this ID already exists!".equals(e.getMessage())) {
                    showAlert("Error", "An employee with this ID already exists!");
                } else {
                    showAlert("Error", "An error occurred while restoring the employee: " + e.getMessage());
                }
            }
        } else {
            showAlert("Selection Error", "Please select an employee to restore.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void editEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            String name = nameField.getText();
            String department = departmentField.getText();
            if (!name.isEmpty() && !department.isEmpty()) {
                selectedEmployee.setName(name);
                selectedEmployee.setDepartment(department);
                employeeDAO.updateEmployee(selectedEmployee);
                loadEmployeeData();
                loadGradeData();
                clearFields();
                showAlert("Information", "Employee details have been updated.");
            } else {
                showAlert("Input Error", "Please fill all fields.");
            }
        } else {
            showAlert("Selection Error", "Please select an employee to edit.");
        }
    }

    @FXML
    public void evaluateEmployee() {
        Employee selectedEmployee = gradeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                double attendance = Double.parseDouble(this.attendance.getText());
                double hardSkill = Double.parseDouble(this.hardskill.getText());
                double softSkill = Double.parseDouble(this.sofskill.getText());

                if (attendance < 0 || attendance > 10 || hardSkill < 0 || hardSkill > 10 || softSkill < 0 || softSkill > 10) {
                    showAlert("Input Error", "Scores must be between 0 and 10.");
                    return;
                }

                double averageScore = (attendance + hardSkill + softSkill) / 3;

                if (!employeeDAO.gradeExists(selectedEmployee.getId())) {
                    employeeDAO.addGrade(selectedEmployee.getId(), averageScore);
                } else {
                    employeeDAO.updateGrade(selectedEmployee.getId(), averageScore);
                }

                loadGradeData();
                clearFields();
                showAlert("Evaluation Result", "The employee's grade is: " + averageScore);
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter valid numerical values.");
            }
        } else {
            showAlert("Selection Error", "Please select an employee to evaluate.");
        }
    }



    @FXML
    public void deleteEmployee() {
        Employee selectedEmployee = trashTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.deleteEmployee(selectedEmployee.getId());
            loadTrashData();
            loadGradeData();
            showAlert("Information", "The employee has been permanently deleted.");
        } else {
            showAlert("Selection Error", "Please select an employee to delete.");
        }
    }


}
