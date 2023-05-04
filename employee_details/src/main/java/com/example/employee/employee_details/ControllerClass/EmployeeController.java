package com.example.employee.employee_details.ControllerClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.employee_details.EntityClass.EmployeeDetails;
import com.example.employee.employee_details.ServiceClass.EmployeeService;

import java.util.*;

@RestController
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/saveEmployee")
    public String saveEmployeeDetails(@RequestBody EmployeeDetails employeeDetails) {
        return employeeService.saveEmployee(employeeDetails);
    }

    @GetMapping("/employeeTaxDeduction")
    public List<EmployeeDetails> employeeTaxInfo() {
        return employeeService.employeeTaxDetails();
    }
}
