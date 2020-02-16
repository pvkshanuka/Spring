package lk.kusal.employee_service_day2.repository;

import lk.kusal.employee_service_day2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
