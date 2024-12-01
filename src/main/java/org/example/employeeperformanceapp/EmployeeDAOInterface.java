package org.example.employeeperformanceapp;

import java.util.List;

public interface EmployeeDAOInterface {
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployee(int employeeId);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int employeeId);
}
