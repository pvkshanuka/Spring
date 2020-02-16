package lk.kusal.employee_service_day3.Service;

import lk.kusal.employee_service_day3.Model.Employee;
import lk.kusal.employee_service_day3.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface EmployeeService {

    Employee test();

    Employee save(Employee employee);

    Employee fetchEmployee(int id);

    List<Employee> fetchAllEmployees();

}
