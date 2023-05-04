package com.example.employee.employee_details.ServiceClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.employee_details.EntityClass.EmployeeDetails;
import com.example.employee.employee_details.RepositoryClass.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String saveEmployee(EmployeeDetails employeeDetails) {
        
        String numberFormat = "\\d+"; // mobile, and salary format (only numbers)
        String nameFormat = "^[a-zA-Z]*$"; // name format which contains only alphabets
        String emailFormat = "^[A-Za-z0-9+_.-]+@(.+)$"; // email format

        // check the match with pattern
        Pattern number = Pattern.compile(numberFormat);
        
        Matcher salaryMatcher = number.matcher(employeeDetails.getSalary());

        Pattern name = Pattern.compile(nameFormat);
        Matcher firstNameMatcher = name.matcher(employeeDetails.getFirstname());
        Matcher lastNameMatcher = name.matcher(employeeDetails.getLastname());

        Pattern email = Pattern.compile(emailFormat);
        Matcher emailMatcher = email.matcher(employeeDetails.getEmail());

        // if the match is found and all conditions are satisfied, employee details will be saved
        int mobileFlag = 0;
        // stroing multiple mobile number as , seperated values.
        for (int i = 0; i < employeeDetails.getMobile().length(); i += 11) {
            if (mobileFlag == 1 || i + 11 >= employeeDetails.getMobile().length()) break;
            Matcher mobilMatcher = number.matcher(employeeDetails.getMobile().substring(i, i+11));
            if(!mobilMatcher.find()) mobileFlag = 1;
        }
        System.out.println(mobileFlag);
        if (mobileFlag == 0 && salaryMatcher.find() && firstNameMatcher.find() && lastNameMatcher.find() && emailMatcher.find()) {
            employeeRepository.save(employeeDetails);
            logger.info("Employee details are successfully added");
            return "Employee details are successfully added";
        }
        logger.error("Fill the data in the correct format");
        return "Fill the data in the correct format";
    }

    @Override
    public List<EmployeeDetails> employeeTaxDetails() {
     
        int daysMonth = 30;
        List<EmployeeDetails> taxAmount = new ArrayList<EmployeeDetails>();
        List<EmployeeDetails> employeeDetails = employeeRepository.findAll();    
        
        for(int i = 0; i < employeeRepository.findAll().size(); i++) {
            EmployeeDetails empDetails = employeeDetails.get(i);
            String dateOfJoining = employeeDetails.get(i).getDoj();
            long yearlySalary = Long.valueOf(employeeDetails.get(i).getSalary()) * 12;
            int date = Integer.valueOf(dateOfJoining.substring(0, 2));
            int month = Integer.valueOf(dateOfJoining.substring(3, 5));
            int daysUnderTax = 0, flag = 0;

            // calculating the days effective under tax
            if (month != 3) daysUnderTax = daysMonth - date + 1;
            for (int j = month + 1; j < 12; j++) daysUnderTax += daysMonth;
            if (month >= 3) {
                daysUnderTax += 90;
            }
            logger.info("The days under tax in the given financial year are : " + daysUnderTax);
            long taxableAmount = (yearlySalary / 360) * daysUnderTax;
            long tax = 0;
            logger.info("The taxable amount is : " + taxableAmount);

            // calculating the tax amount and updating in db
            if (taxableAmount <= 250000) {
                empDetails.setCess_amount("No tax requied to pay");
                flag = 1;
            }
            else if (taxableAmount > 250000 && taxableAmount <= 500000) {
                tax = ((taxableAmount - 250000) / 100) * 5;  
            }
            else if (taxableAmount > 500000 && taxableAmount <=1000000) {
                tax = (250000 / 100) * 5;
                tax += ((taxableAmount - 500000) / 100) * 10;
            }
            else {
                tax = (250000 / 100) * 5;
                tax += (500000 / 100) * 10;
                tax += ((taxableAmount - 1000000) / 100) * 20;
            }

            if (taxableAmount > 2500000) {
                tax += ((taxableAmount - 2500000)/100) * 2;
            }
            logger.info("The tax amount is : " + tax);
            if (flag == 0) empDetails.setCess_amount("Tax amount requied to pay is : " + tax);
            empDetails.setTaxamount(String.valueOf(taxableAmount)); empDetails.setYearlysalary(String.valueOf(yearlySalary));
            taxAmount.add(new EmployeeDetails(empDetails.getEmployeeid(), empDetails.getFirstname(), empDetails.getLastname(), empDetails.getEmail(), empDetails.getCess_amount(), String.valueOf(taxableAmount), String.valueOf(yearlySalary)));
        }
        return taxAmount;
    }
    
}
