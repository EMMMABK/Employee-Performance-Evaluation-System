package org.example.employeeperformanceevaluationsystem;

import javafx.collections.ObservableList;

public class EmployeePerformanceApp {

    private ObservableList<Employee> employeeList;

    public EmployeePerformanceApp(ObservableList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public void editEmployee(int id, String fullname, String department) {
        for (Employee e : employeeList) {
            if (e.getId() == id) {
                e.setFullname(fullname);
                e.setDepartment(department);
                break;
            }
        }
    }

    public void deleteEmployee(int id) {
        employeeList.removeIf(e -> e.getId() == id);
    }

    public ObservableList<Employee> getEmployeeList() {
        return employeeList;
    }
}
