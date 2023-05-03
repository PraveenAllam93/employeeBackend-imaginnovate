package com.example.employee.employee_details.ServiceClass;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.employee_details.EntityClass.EmployeeDetails;
import com.example.employee.employee_details.RepositoryClass.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String saveEmployee(EmployeeDetails employeeDetails) {
        
        String numberFormat = "\\d+"; // mobile, and salary format (only numbers)
        String nameFormat = "^[a-zA-Z]*$"; // name format which contains only alphabets
        String emailFormat = "^[A-Za-z0-9+_.-]+@(.+)$"; // email format

        // check the match with pattern
        Pattern number = Pattern.compile(numberFormat);
        Matcher mobileNumberMatcher = number.matcher(employeeDetails.getMobile());
        Matcher salaryMatcher = number.matcher(employeeDetails.getSalary());

        Pattern name = Pattern.compile(nameFormat);
        Matcher firstNameMatcher = name.matcher(employeeDetails.getFirstname());
        Matcher lastNameMatcher = name.matcher(employeeDetails.getLastname());

        Pattern email = Pattern.compile(emailFormat);
        Matcher emailMatcher = email.matcher(employeeDetails.getEmail());

        // if the match is found and all conditions are satisfied, employee details will be saved
        if (mobileNumberMatcher.find() && salaryMatcher.find() && firstNameMatcher.find() && lastNameMatcher.find() && emailMatcher.find() && (employeeDetails.getMobile().length() == 10)) {
            employeeRepository.save(employeeDetails);
            return "Employee details are successfully added";
        }
        // if match is not found error is thrown (we can use logger to track)
        else {
            if(!mobileNumberMatcher.find()) return "Please do enter correct mobile number";
            if(!salaryMatcher.find()) return "Please do enter correct salary";
            if(!firstNameMatcher.find()) return "First name contains only a-z/A-Z";
            if(!lastNameMatcher.find()) return "Last name contains only a-z/A-Z";
            if(!emailMatcher.find()) return "Please do enter correct email";
        }
        return "Fill the data in the correct format";
    }

    @Override
    public List<EmployeeDetails> employeeTaxDetails() {
        // days in respective month
        int[] monthDays = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        List<EmployeeDetails> employeeDetails = employeeRepository.findAll();

        for(int i = 0; i < employeeRepository.findAll().size(); i++) {
            // getting month & date from doj
            String dateOfJoining = employeeDetails.get(i).getDoj();
            long salary = Long.valueOf(employeeDetails.get(i).getSalary());
            int date = Integer.valueOf(dateOfJoining.substring(0, 2));
            int month = Integer.valueOf(dateOfJoining.substring(3, 5));
            int daysUnderTax = 0;
            // calculating the days effective under tax
            if (month != 3) daysUnderTax = monthDays[month - 1] - date + 1;
            for (int j = month; j < 12; j++) daysUnderTax += monthDays[j];
            if (month >= 3) {
                daysUnderTax += 90;
            }
            System.out.println("The days under tax in the given financial year are : " + daysUnderTax);
            long taxableAmount = (salary / 365) * daysUnderTax;
            System.out.println("The taxable amount is : " + taxableAmount);
            // calculating the tax amount and updating in db
            if (taxableAmount <= 250000) employeeDetails.get(i).setCess_amount("No tax requied to pay");
            else if (taxableAmount > 250000 && taxableAmount <= 500000) {
                long tax = ((taxableAmount - 250000) / 100) * 5;
                employeeDetails.get(i).setCess_amount("Tax amount requied to pay is : " + tax);
            }
            else if (taxableAmount > 500000 && taxableAmount <=1000000) {
                long tax = (250000 / 100) * 5;
                tax += ((taxableAmount - 500000) / 100) * 10;
                employeeDetails.get(i).setCess_amount("Tax amount requied to pay is : " + tax);
            }
            else {
                long tax = (250000 / 100) * 5;
                tax += (500000 / 100) * 10;
                tax += ((taxableAmount - 1000000) / 100) * 20;
                employeeDetails.get(i).setCess_amount("Tax amount requied to pay is : " + tax);
            }

        }
        return employeeRepository.findAll();
    }
    
}
