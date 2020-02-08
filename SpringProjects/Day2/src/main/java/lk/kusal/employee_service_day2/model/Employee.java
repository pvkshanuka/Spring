package lk.kusal.employee_service_day2.model;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String marks;

    @OneToOne(cascade = CascadeType.ALL)
    Address address;

    @OneToMany(targetEntity = Telephone.class, mappedBy = "employee")
    List<Telephone> telephones;

    @ManyToMany
    @JoinTable(joinColumns={@JoinColumn(name="e_id", referencedColumnName = "id")}, inverseJoinColumns={@JoinColumn(name="p_id", referencedColumnName = "id")} )
    List<Project> projects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }
}
