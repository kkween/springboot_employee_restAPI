package com.izooki.springboottesting.service;

import com.izooki.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee savedEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Employee updateEmployee(Employee updatedEmployee);
    void deleteEmployee(long id);
}
