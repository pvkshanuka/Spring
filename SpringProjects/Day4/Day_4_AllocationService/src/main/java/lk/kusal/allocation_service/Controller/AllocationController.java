package lk.kusal.allocation_service.Controller;

import lk.kusal.allocation_service.Model.Allocation;
import lk.kusal.allocation_service.Service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alloservice")
public class AllocationController {

    @Autowired
    AllocationService allocationService;

    @RequestMapping("/test")
    public Allocation test() {

        Allocation allocation = new Allocation();
        allocation.setId(1);
        allocation.setEmpid(1);
        allocation.setStart("2018");
        allocation.setEnd("2020");
        allocation.setProjectCode("ABC");

        return allocation;

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Allocation save(@RequestBody Allocation allocation) {
        return allocationService.save(allocation);
    }

    @RequestMapping("/fetch_all")
    public List<Allocation> fetchAllAlocations() {
        return allocationService.fetchAllAlocations();
    }

    @RequestMapping("/fetch/{id}")
    public Allocation fetchAllocation(@PathVariable int id) {
        return allocationService.fetchAllocation(id);
    }

    @RequestMapping("/fetch_f_emp/{empid}")
    public List<Allocation> fetchFromEmp(@PathVariable int empid) {
        return allocationService.fetchFromEmp(empid);
    }

}
