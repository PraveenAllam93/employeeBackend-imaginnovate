package com.example.employee.employee_details.EntityClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EmployeeData")
public class EmployeeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "employeeid")
    private String employeeid;
    @Column(name = "firstname")
    private String firstname;   
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;  
    @Column(name = "mobile")
    private String mobile;   
    @Column(name = "doj") 
    private String doj;        
    @Column(name = "salary")
    private String salary;
    @Column(name = "cess_amount")
    private String cess_amount;
    
    public String getCess_amount() {
        return cess_amount;
    }

    public void setCess_amount(String cess_amount) {
        this.cess_amount = cess_amount;
    }

    public EmployeeDetails() {
        super();
    }

    public EmployeeDetails(String employeeid, String firstname, String lastname, String email, String mobile,
            String doj, String salary, String cess_amount) {
        this.employeeid = employeeid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
        this.doj = doj;
        this.salary = salary;
        this.cess_amount = cess_amount;
    }
    public String getEmployeeid() {
        return employeeid;
    }
    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getDoj() {
        return doj;
    }
    public void setDoj(String doj) {
        this.doj = doj;
    }
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

       
}