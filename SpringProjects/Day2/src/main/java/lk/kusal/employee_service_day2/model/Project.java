package lk.kusal.employee_service_day2.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    @ManyToMany(mappedBy = "projects")
    List<Employee> employees;
}
