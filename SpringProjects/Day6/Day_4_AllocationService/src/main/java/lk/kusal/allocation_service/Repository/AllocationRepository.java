package lk.kusal.allocation_service.Repository;

import lk.kusal.allocation_service.Model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationRepository extends JpaRepository<Allocation,Integer> {
}
