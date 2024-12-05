# Employee Performance Management System

## Table of Contents
1. [Description](#description)
2. [Project Requirements List](#project-requirements-list)
3. [Team Members List](#team-members-list)
4. [Roles of Group Members](#roles-of-group-members)
5. [Screenshots](#screenshots)
6. [UML Class Diagram](#uml-class-diagram)
7. [Weekly Meeting Documentation](#weekly-meeting-documentation)
8. [Commit History](#commit-history)
9. [OOP Concepts and questions](#oop-concepts-and-questions-)
## Description
The Employee Performance Evaluation System helps an organization manage and assess its employees' performance efficiently. It allows the user to add, edit, delete, and even move employee records to the trash. An employee's performance is also tracked through an evaluation system based on attendance, soft skills, and hard skills. This will enable the HR team to effectively manage and evaluate the overall performance of the employees. Besides, it has functions for managing and restoring deleted employee records.

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
- **Adil Bikiev** - Project Manager, Database Administrator, Backend Developer
- **Yryskeldi Bakhapov** - Tester, UI/UX Designer, Content Creator
- **Altynbek Zhonguchkaev** - Database Administrator, Backend Developer, Frontend Developer

## Roles of Group Members
- **Adil Bikiev**: Managed the overall project, implemented the backend logic, and integrated database functions.
- **Yryskeldi Bakhapov**: Designed the user interface using JavaFX and SceneBuilder. Handled front-end integration.
- **Altynbek Zhonguchkaev**: Managed the database schema, created the `EmployeeDAO` class, and handled database connections and queries.

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
Weekly meeting summaries and action items can be found in the [Google Docs link](https://docs.google.com/document/d/1E4ld5ssIVgZshUG-lhLDjgzNAuSk3Rkg3Ik92CVwCgI/edit?usp=sharing).

## Commit History
The commit history has been tracked and is available in the GitHub repository. Commits started more than 3 weeks before the submission date to ensure proper version control and collaboration.

## OOP Concepts and questions

## 1. **Encapsulation**
**Explanation:**  
Encapsulation is used to protect data by making fields private and providing public getter and setter methods to access and modify them.

**Code Example:**
```java
public class Employee {
    private final StringProperty name;
    private final StringProperty department;
    private final Date hireDate;
    private int id;

    public Employee(int id, String name, String department, Date hireDate) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
        this.hireDate = hireDate;
    }

    // Getter for name
    public String getName() {
        return name.get();
    }

    // Setter for name
    public void setName(String name) {
        this.name.set(name);
    }
}
```

## 2. **Access Modifiers**
**Explanation:**  
Access modifiers control the visibility of classes, methods, and variables. `private` hides the fields from outside, while `public` makes them accessible.

**Code Example:**
```java
public class EmployeeDAO {
    private final Connection connection;  // private access to the connection

    // public method accessible from outside
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (name, department, hire_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setDate(3, new java.sql.Date(employee.getHireDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## 3. **Constructor**
**Explanation:**  
Constructors are used to initialize objects with a valid state at the time of creation.

**Code Example:**
```java
public Employee(int id, String name, String department, Date hireDate) {
    this.id = id;
    this.name = new SimpleStringProperty(name);
    this.department = new SimpleStringProperty(department);
    this.hireDate = hireDate;
}
```

## 4. **Method Overloading**
**Explanation:**  
Method overloading allows multiple methods with the same name but different parameters, enabling the handling of different input types.

**Code Example:**
```java
public void addGrade(int employeeId, double grade) {
    // Add grade to employee
}

public void addGrade(EmployeeGrade employeeGrade) {
    // Add grade from EmployeeGrade object
}
```

## 5. **Exception Handling**
**Explanation:**  
Exception handling is used to manage errors, ensuring the application does not crash due to unexpected issues.

**Code Example:**
```java
try {
    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
} catch (SQLException e) {
    e.printStackTrace();
    showAlert("Database Error", "Could not connect to the database.");
}
```

## 6. **Inheritance**
**Explanation:**  
Inheritance allows a class (e.g., `EmployeeGrade`) to reuse properties and methods from a parent class (`Employee`).

**Code Example:**
```java
public class EmployeeGrade extends Employee {
    private double grade;

    public EmployeeGrade(int id, String name, String department, Date hireDate, double grade) {
        super(id, name, department, hireDate);  // Inherited constructor
        this.grade = grade;
    }
}
```

## 7. **Method Overriding**
**Explanation:**  
Method overriding allows subclasses to provide specific implementations of methods defined in a superclass.

**Code Example:**
```java
@Override
public String getName() {
    return super.getName();  // Overriding the getName method
}
```

## 8. **Interface**
**Explanation:**  
Interfaces define common behavior for classes to implement, ensuring consistency across different implementations.

**Code Example:**
```java
public interface EmployeeDAOInterface {
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
}

public class EmployeeDAO implements EmployeeDAOInterface {
    @Override
    public void addEmployee(Employee employee) {
        // Implement add logic
    }

    @Override
    public void updateEmployee(Employee employee) {
        // Implement update logic
    }
}
```

## 9. **Polymorphism**
**Explanation:**  
Polymorphism allows the handling of objects of different types in a uniform way, typically through a common interface or superclass.

**Code Example:**
```java
List<Employee> employees = new ArrayList<>();
employees.add(new Employee(1, "John", "HR", new Date()));
employees.add(new EmployeeGrade(2, "Alice", "Engineering", new Date(), 85));

// Polymorphism in action
for (Employee e : employees) {
    System.out.println(e.getName());  // Works for both Employee and EmployeeGrade
}
```

## 10. **Dependency Injection**
**Explanation:**  
Dependency Injection reduces tight coupling by passing dependencies (e.g., `EmployeeDAO`) to a class rather than creating them inside.

**Code Example:**
```java
public class HelloController {
    private EmployeeDAO employeeDAO;

    // Constructor Injection
    public HelloController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void loadEmployeeData() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employeeTable.getItems().setAll(employees);
    }
}
```

## GitHub Repository
The project source code and documentation can be accessed on GitHub at [GitHub Repository Link](link-to-your-repository).
