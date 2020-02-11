package lk.kusal.allocation_service.Model;

import javax.persistence.*;

@Entity
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer empid;
    String start;
    String end;
    String projectCode;

    @Basic(optional = true)
    @Column(nullable = false)
    int empCount;

    public Allocation() {
    }

    public Allocation(Integer empid, String start, String end, String projectCode, int empCount) {
        this.empid = empid;
        this.start = start;
        this.end = end;
        this.projectCode = projectCode;
        this.empCount = empCount;
    }

    public Allocation(Integer empid, String start, String end, String projectCode) {
        this.empid = empid;
        this.start = start;
        this.end = end;
        this.projectCode = projectCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public int getEmpCount() {
        return empCount;
    }

    public void setEmpCount(int empCount) {
        this.empCount = empCount;
    }
}
