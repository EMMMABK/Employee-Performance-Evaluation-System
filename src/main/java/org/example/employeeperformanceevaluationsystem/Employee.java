package org.example.employeeperformanceevaluationsystem;

public class Employee {
    private static int idCounter = 1; // Счётчик для уникальных ID сотрудников
    private int id;
    private String fullName;
    private String department;
    private boolean evaluated;

    // Конструктор
    public Employee(String fullName, String department) {
        this.id = idCounter++;
        this.fullName = fullName;
        this.department = department;
        this.evaluated = false;  // Сотрудник ещё не оценен
    }

    // Геттеры и сеттеры
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
        this.fullName = fullName;  // Сеттер для fullName
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    // Вычисление средней оценки (для примера, в реальной задаче, вероятно, будет другая логика)
    public double calculateAverage() {
        // Здесь можно реализовать логику подсчета средней оценки для сотрудника
        return 10.0; // Пример, возвращаем фиксированную оценку
    }
}

