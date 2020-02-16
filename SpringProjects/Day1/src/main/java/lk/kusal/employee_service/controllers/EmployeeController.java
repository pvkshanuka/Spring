package lk.kusal.employee_service.controllers;

import lk.kusal.employee_service.models.Employee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class EmployeeController {

    @RequestMapping("/test")
    public String test(){
        return "<h1>test</h1>";
    }

    @RequestMapping("/getEmployees")
    public List<Employee> getEmployees(){
        return Employee.getAllEmployees();
    }

//    @RequestMapping(value = "/getEmployeesXML", produces = {MediaType.APPLICATION_XML_VALUE})
//    public List<Employee> getEmployeesXML(){
//        return Employee.getAllEmployees();
//    }

}
