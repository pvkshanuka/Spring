package lk.e_channeling.hospital_service.repository;

import lk.e_channeling.hospital_service.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital,Integer> {

    public Optional<Hospital> findByIdAndStatus(Integer id, String status);

}
