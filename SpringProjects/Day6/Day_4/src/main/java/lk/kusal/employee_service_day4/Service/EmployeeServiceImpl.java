package lk.kusal.employee_service_day4.Service;

import lk.kusal.employee_service_day4.Model.Address;
import lk.kusal.employee_service_day4.Model.Employee;
import lk.kusal.employee_service_day4.Model.Telephone;
import lk.kusal.employee_service_day4.Repository.EmployeeRepository;
import lk.kusal.employee_service_day4.common_model.Allocation;
import lk.kusal.employee_service_day4.hystrix.AllocationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

    @Override
    public Employee test() {

        Address address = new Address("TestCity");

        Employee employee = new Employee("Test", address, null, null);

        return employee;
    }

    @Override
    public Employee save(Employee employee) {

        for (Telephone telephone : employee.getTelephones()) {
            telephone.setEmployee(employee);
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee fetchEmployee(int id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {

            Employee employee = optional.get();

//            ResponseEntity<Allocation[]> responseEntity = restTemplate.exchange("http://alloservice/alloservice/fetch_f_emp/" + id, HttpMethod.POST, httpEntity, Allocation[].class);
//
//            employee.setAllocations(responseEntity.getBody());

            employee.setAllocations(fetchAllocations(employee));

            return employee;
        } else
            return null;

    }

    @Override
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Allocation[] fetchAllocations(Employee employee) {
        AllocationCommand allocationCommand =  new AllocationCommand(employee,httpHeaders,restTemplate);
        return allocationCommand.execute();
    }
}
