package com.example.employee.employee_details.ServiceClass;

import java.util.*;
import com.example.employee.employee_details.EntityClass.EmployeeDetails;

public interface EmployeeService {

    // to save the employyee details 
    String saveEmployee(EmployeeDetails employeeDetails);
    
    // to find the taxable amount
    List<EmployeeDetails> employeeTaxDetails();
}
