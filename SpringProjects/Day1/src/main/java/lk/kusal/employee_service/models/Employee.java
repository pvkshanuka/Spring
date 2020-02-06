package lk.kusal.employee_service.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class Employee {

    String name;
    String marks;

    public Employee() {
        System.out.println("Constructor Executed");
    }

    public Employee(String name, String marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public HashMap<String,String> getMarks() {
//        HashMap<String,String> strings = new HashMap<>();
//        strings.put("name",name);
//        strings.put("marks",marks);
//        return strings;
//    }

    public String getMarks(){
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public static List<Employee> getAllEmployees(){

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Shanuka","85"));
        employees.add(new Employee("Thusitha","55"));
        employees.add(new Employee("Sandun","67"));
        employees.add(new Employee("Kusal","92"));
        employees.add(new Employee("Dishan","74"));

        return employees;

    }

}
