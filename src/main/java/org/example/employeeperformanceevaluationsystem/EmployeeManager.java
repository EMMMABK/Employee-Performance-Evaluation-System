package org.example.employeeperformanceevaluationsystem;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private final List<Employee> employees;
    private final List<Evaluation> evaluations;

    public EmployeeManager() {
        employees = new ArrayList<>();
        evaluations = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public void editEmployee(Employee employee, String fullName, String department) {
        employee.setFullName(fullName);
        employee.setDepartment(department);
    }

    public void evaluateEmployee(Employee employee, double projectScore, double colleagueScore, double attendanceScore) {
        Evaluation evaluation = new Evaluation(employee, projectScore, colleagueScore, attendanceScore);
        evaluations.add(evaluation);
        employee.setEvaluated(true); // Обновляем статус сотрудника
        employees.remove(employee);  // Убираем из списка сотрудников
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
}
