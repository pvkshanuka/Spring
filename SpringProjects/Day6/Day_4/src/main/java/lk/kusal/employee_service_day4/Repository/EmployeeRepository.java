package lk.kusal.employee_service_day4.Repository;

import lk.kusal.employee_service_day4.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
