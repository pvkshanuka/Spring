package lk.kusal.employee_service_day3.Service;

import lk.kusal.employee_service_day3.Model.Address;
import lk.kusal.employee_service_day3.Model.Employee;
import lk.kusal.employee_service_day3.Model.Telephone;
import lk.kusal.employee_service_day3.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee test() {

        Address address = new Address("TestCity");

        Employee employee = new Employee("Test", address,null,null);

        return employee;
    }

    @Override
    public Employee save(Employee employee) {

        for (Telephone telephone:employee.getTelephones()) {
            telephone.setEmployee(employee);
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee fetchEmployee(int id) {
        return employeeRepository.getOne(id);
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }
}
