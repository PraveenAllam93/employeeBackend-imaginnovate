package com.example.employee.employee_details.RepositoryClass;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employee.employee_details.EntityClass.EmployeeDetails;

public interface EmployeeRepository extends JpaRepository<EmployeeDetails, String>{
    
}
