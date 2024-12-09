package org.example.employeeperformanceapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class EmployeeTest {

    @Test
    void testGettersAndSetters() {
        System.out.println("Starting testGettersAndSetters...");

        Date hireDate = new Date();
        Employee employee = new Employee(1, "John Doe", "Engineering", hireDate);

        assertEquals(1, employee.getId());
        System.out.println("Employee ID is correct");

        assertEquals("John Doe", employee.getName());
        System.out.println("Employee Name is correct");

        assertEquals("Engineering", employee.getDepartment());
        System.out.println("Employee Department is correct");

        assertEquals(hireDate, employee.getHireDate());
        System.out.println("Employee HireDate is correct");

        employee.setId(2);
        employee.setName("Jane Smith");
        employee.setDepartment("Marketing");

        assertEquals(2, employee.getId());
        System.out.println("Employee ID updated correctly");

        assertEquals("Jane Smith", employee.getName());
        System.out.println("Employee Name updated correctly");

        assertEquals("Marketing", employee.getDepartment());
        System.out.println("Employee Department updated correctly");

        System.out.println("testGettersAndSetters completed successfully!");
    }

    @Test
    void testNameProperty() {
        System.out.println("Starting testNameProperty...");

        Employee employee = new Employee(1, "John Doe", "Engineering", new Date());

        employee.setName("Alice");
        assertEquals("Alice", employee.nameProperty().get());
        System.out.println("Employee Name property updated correctly");

        assertEquals("Alice", employee.getName());
        System.out.println("Employee Name updated correctly");

        System.out.println("testNameProperty completed successfully!");
    }

    @Test
    void testDepartmentProperty() {
        System.out.println("Starting testDepartmentProperty...");

        Employee employee = new Employee(1, "John Doe", "Engineering", new Date());

        employee.setDepartment("HR");
        assertEquals("HR", employee.departmentProperty().get());
        System.out.println("Employee Department property updated correctly");

        assertEquals("HR", employee.getDepartment());
        System.out.println("Employee Department updated correctly");

        System.out.println("testDepartmentProperty completed successfully!");
    }
}


