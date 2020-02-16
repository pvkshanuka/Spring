package lk.kusal.employee_service_day4.Service;

import lk.kusal.employee_service_day4.Model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee test();

    Employee save(Employee employee);

    Employee fetchEmployee(int id);

    List<Employee> fetchAllEmployees();

}
