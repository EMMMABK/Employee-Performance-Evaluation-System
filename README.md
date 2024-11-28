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


## OOP Concepts and questions

 *1. Encapsulation: How is encapsulation applied in the project to protect data and control access?*

 * Private fields: Fields like id, fullName, department, and evaluation are declared as private, preventing direct access from outside the class.
    ```
    private int id;
    private String fullName;
    private String department;
    private Integer evaluation;
    ```

 * Public getter and setter methods: These methods provide controlled access to the private fields, allowing validation or processing before updating or retrieving values.
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


*2. Access Modifiers: How do access modifiers (private, public, protected) help manage visibility and security in the project?*
 *  Private: Limits access to within the class, protecting data from external modification.
    ```
    private int id;
    private String fullName;
    private String department;
    private Integer evaluation;
    ```
 *  Public: Allows access from other classes through methods.
    ```
    public Integer getEvaluation() {
    return evaluation;
    }
   
    public void setEvaluation(Integer evaluation) {
       if (evaluation >= 0 && evaluation <= 10) {
           this.evaluation = evaluation;
       } else {
           throw new IllegalArgumentException("Evaluation must be between 0 and 10");
       }
    }

    ```
    
*3. Constructor: How are constructors used in the project to initialize objects with valid state?*
 * The constructor in the Employee class initializes each employee with an ID, full name, department, and sets the evaluation to null initially
   ```
    public Employee(int id, String fullName, String department) {
    this.id = id;
    this.fullName = fullName;
    this.department = department;
    this.evaluation = null; // Default evaluation is null until assessed
   }
   ```
*4. Method Overloading: How is method overloading utilized in the project to handle different input types or parameters?*
 * ответ
