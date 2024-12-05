# Employee Performance Management System

## Table of Contents
1. [Description](#description)
2. [Project Requirements List](#project-requirements-list)
3. Team Members List
4. Roles of Group Members
5. Screenshots
6. UML Class Diagram
7. Weekly Meeting Documentation
8. Commit History
9. OOP Concepts and questions

## Description
The Employee Performance Management System is designed to help companies efficiently manage and evaluate their employees. It enables adding, editing, deleting, and moving employee records to a trash folder. Employees' performance is also tracked through an evaluation system that considers attendance, soft skills, and hard skills, allowing the HR team to manage and assess employees' overall performance. The system also includes functionalities to manage and restore deleted employee records.

## Project Requirements List
The following key functionalities are essential for the completion of the project:
1. **Add Employee**: Add new employees to the system with basic information such as name, department, and hire date.
2. **Edit Employee**: Modify employee details like name and department.
3. **Delete Employee**: Delete an employee’s record and move it to a trash table.
4. **Restore Employee**: Restore a deleted employee’s record from the trash table back to the main employee table.
5. **Evaluate Employee**: Assess employee performance based on attendance, soft skills, and hard skills, then calculate an overall grade.
6. **View All Employees**: Display a list of all employees with relevant details.
7. **View Trash**: View deleted employees’ details in the trash table.
8. **Employee Grades**: View and update performance grades for employees based on evaluations.
9. **Grade Existence Check**: Ensure that grades are only added or updated for existing employees.
10. **Clear Input Fields**: Automatically clear the input fields after adding or editing employee records.

## Team Members List
- **John Doe** - Project Manager, Backend Developer
- **Jane Smith** - Frontend Developer, UI/UX Designer
- **Alice Johnson** - Database Administrator, Tester
- **Bob Brown** - HR Consultant, Documentation and GitHub Management

## Roles of Group Members
- **John Doe**: Managed the overall project, implemented the backend logic, and integrated database functions.
- **Jane Smith**: Designed the user interface using JavaFX and SceneBuilder. Handled front-end integration.
- **Alice Johnson**: Managed the database schema, created the `EmployeeDAO` class, and handled database connections and queries.
- **Bob Brown**: Coordinated the project’s HR requirements, helped design workflows for employee management, and maintained GitHub repositories.

## Screenshots
Below are key screenshots showcasing the application:
- **Employee Management Screen**: ![Employee Management](link-to-screenshot.jpg)
- **Trash Table**: ![Trash Table](link-to-screenshot2.jpg)
- **Evaluation Screen**: ![Evaluation Screen](link-to-screenshot3.jpg)

## UML Class Diagram
The UML class diagram provides a visual representation of the system’s structure, detailing the classes, attributes, methods, and their relationships.

![UML Diagram](link-to-uml-diagram.jpg)

### Diagram Description:
- **Employee**: Represents employee details with attributes like `name`, `department`, and `hireDate`.
- **EmployeeGrade**: Extends `Employee` and adds an additional property `grade` to store the evaluation score.
- **EmployeeDAO**: Handles database operations for managing employees and grades.
- **EmployeeDAOInterface**: Interface for CRUD operations related to employee records.
- **HelloController**: JavaFX controller that handles UI interactions, including employee addition, modification, deletion, and evaluation.

## Weekly Meeting Documentation
Weekly meeting summaries and action items can be found in the [Google Docs link](link-to-google-docs).

## Commit History
The commit history has been tracked and is available in the GitHub repository. Commits started more than 3 weeks before the submission date to ensure proper version control and collaboration.

## GitHub Repository
The project source code and documentation can be accessed on GitHub at [GitHub Repository Link](link-to-your-repository).
