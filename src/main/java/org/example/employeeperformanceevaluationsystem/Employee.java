package org.example.employeeperformanceevaluationsystem;

public class Employee {
    private static int idCounter = 1;  // Счётчик для уникальных ID сотрудников
    private int id;
    private String fullName;
    private String department;  // Поле для хранения отдела
    private boolean evaluated;  // Флаг, указывающий, был ли сотрудник оценён

    // Конструктор, инициализирующий все поля
    public Employee(String fullName, String department) {
        this.id = idCounter++;  // Уникальный ID для каждого сотрудника
        this.fullName = fullName;
        this.department = department;
        this.evaluated = false;  // Сотрудник ещё не оценен
    }

    // Геттеры и сеттеры для каждого поля

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
        this.fullName = fullName;  // Устанавливаем имя сотрудника
    }

    public String getDepartment() {
        return department;  // Возвращаем отдел сотрудника
    }

    public void setDepartment(String department) {
        this.department = department;  // Устанавливаем отдел сотрудника
    }

    public boolean isEvaluated() {
        return evaluated;  // Проверка, был ли сотрудник оценен
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;  // Устанавливаем статус оценки
    }

    // Метод для вычисления средней оценки (например, по проекту, коллегам и посещаемости)
    public double calculateAverage(double projectScore, double colleagueScore, double attendanceScore) {
        return (projectScore + colleagueScore + attendanceScore) / 3.0;
    }
}

