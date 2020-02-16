package lk.kusal.employee_service_day2.controller;

import lk.kusal.employee_service_day2.model.Employee;
import lk.kusal.employee_service_day2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/test")
    public String test(){
        return "<h1>TEST</h1>";
    }

    @RequestMapping(value = "employee/save", method = RequestMethod.POST)
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

}
