package lk.kusal.allocation_service.Service;

import lk.kusal.allocation_service.Model.Allocation;
import lk.kusal.allocation_service.Repository.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    AllocationRepository allocationRepository;

    @Override
    public Allocation save(Allocation allocation) {
        return allocationRepository.save(allocation);
    }

    @Override
    public List<Allocation> fetchAllAlocations() {
        return allocationRepository.findAll();
    }

    @Override
    public Allocation fetchAllocation(int id) {

        Optional<Allocation> optional = allocationRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
            return null;
    }

    @Override
    public List<Allocation> fetchFromEmp(int empid) {

        Allocation allocation = new Allocation();
        allocation.setEmpid(empid);

        Example<Allocation> example = Example.of(allocation);

        return allocationRepository.findAll(example);

    }
}
