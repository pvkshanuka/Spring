package lk.kusal.allocation_service.Service;

import lk.kusal.allocation_service.Model.Allocation;

import java.util.List;

public interface AllocationService {

    Allocation save(Allocation allocation);

    List<Allocation> fetchAllAlocations();

    Allocation fetchAllocation(int id);

    List<Allocation> fetchFromEmp(int empid);

}
