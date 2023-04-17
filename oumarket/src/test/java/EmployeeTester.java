/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.Employee;
//package services;
import java.sql.SQLException;
import java.util.List;
import services.EmployeeService;
public class EmployeeTester {
    




    @Test
    void testGetEmployees() throws SQLException {
        EmployeeService employeeService = new EmployeeService();
        List<Employee> employeeList = employeeService.getEmployees("");
        Assertions.assertEquals(4, employeeList.size());
    }

    @Test
    void testDeleteEmployee() throws SQLException {
        EmployeeService employeeService = new EmployeeService();
        boolean result = employeeService.deleteEmployee(4);
        Assertions.assertTrue(result);

        List<Employee> employeeList = employeeService.getEmployees("");
        Assertions.assertEquals(3, employeeList.size());
    }

    @Test
    void testInsert() throws SQLException {
        EmployeeService employeeService = new EmployeeService();
        Employee employee = new Employee(0, "John");
        employeeService.insert(employee);

        List<Employee> employeeList = employeeService.getEmployees("");
        Assertions.assertEquals(5, employeeList.size());
    }

    @Test
    void testUpdate() throws SQLException {
        EmployeeService employeeService = new EmployeeService();
        Employee employee = new Employee(1, "Adam");
        boolean result = employeeService.update(employee);
        Assertions.assertTrue(result);

        List<Employee> employeeList = employeeService.getEmployees("");
        Assertions.assertEquals("Adam", employeeList.get(0).getName());
    }
}


