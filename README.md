# Employee Evaluation System

## Table of Contents
1. [Description](#description)
2. [Project Requirements List](#project-requirements-list)
3. [Team Members List](#team-members-list)
4. [Roles of Group Members](#roles-of-group-members)
5. [Screenshots](#screenshots)
6. [UML Class Diagram](#uml-class-diagram)
7. [Weekly Meeting Documentation](#weekly-meeting-documentation)
8. [Commit History](#commit-history)
9. [OOP Concepts and questions](#OOP-Concepts-and-questions)

## Description
The **Employee Evaluation System** is a JavaFX-based application designed to manage and evaluate employee performance in various departments. It allows users to view employee details, evaluate their performance based on various criteria (Hard Skills, Soft Skills, and Attendance), and store evaluations for future reference. The system includes features for adding, editing, removing employees, and generating performance evaluations.

## Project Requirements List
1. Display a list of employees with their ID, full name, department, and evaluation score.
2. Allow users to add new employees with basic details (ID, full name, department).
3. Provide the ability to edit an employee's details.
4. Allow users to remove employees from the list.
5. Provide an "Evaluate" button to assess employee performance.
6. Implement a scoring system for evaluating employees on Hard Skills, Soft Skills, and Attendance (0-10 scale).
7. Store and display evaluation scores for each employee.
8. Prevent re-evaluation of employees unless edited via the "Edit" function.
9. Implement error handling for invalid inputs (e.g., invalid evaluation scores).
10. Store employee information and evaluation data persistently within the application.

## Team Members List
- **John Doe** – Project Manager, Developer
- **Jane Smith** – Frontend Developer
- **Alice Johnson** – Backend Developer
- **Bob Brown** – QA Tester

## Roles of Group Members
- **John Doe**: Lead development of the backend logic and implementation of evaluation scoring system.
- **Jane Smith**: Responsible for designing and implementing the user interface using JavaFX.
- **Alice Johnson**: Assisted with backend development, focusing on data management and validation logic.
- **Bob Brown**: Conducted testing of the system, ensuring functionality and bug reporting.

## Screenshots
(Include screenshots of key features, such as the employee list view, the evaluation form, and the employee details screen. These can be added after project completion.)

## UML Class Diagram
(Include a UML diagram that visualizes the relationships between the classes like `Employee`, `FullTimeEmployee`, `PartTimeEmployee`, etc. This can be created using a UML tool and included in your project folder.)

## Weekly Meeting Documentation
All meeting notes will be documented on **Google Docs** and uploaded to the repository. These documents will summarize discussions, decisions made, and action items. The weekly meeting summaries will provide insight into the project's progress and any challenges faced.

## Commit History
The commit history for this project is available in the GitHub repository. Commits started 3 weeks before the submission date, and the history includes detailed notes on development progress, bug fixes, and feature implementations.

## OOP Concepts and questions

1. Encapsulation: How is encapsulation applied in the project to protect data and control access?
Private fields: Fields like id, fullName, department, and evaluation are declared as private, preventing direct access from outside the class.
```
private int id;
private String fullName;
private String department;
private Integer evaluation;
```

Public getter and setter methods: These methods provide controlled access to the private fields, allowing validation or processing before updating or retrieving values.
```
public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getFullName() {
    return fullName;
}

public void setFullName(String fullName) {
    this.fullName = fullName;
}

public String getDepartment() {
    return department;
}

public void setDepartment(String department) {
    this.department = department;
}

public Integer getEvaluation() {
    return evaluation;
}

public void setEvaluation(Integer evaluation) {
    this.evaluation = evaluation;
}

```


2. Access Modifiers
3. Constructor
4. Method Overloading
5. Exception Handling
6. Inheritance and Abstract Classes
7. Method Overriding
8. Interface
9. Polymorphism
10. Dependency Injection
