package lk.e_channeling.hospital_service.repository;

import lk.e_channeling.hospital_service.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital,Integer> {

    public List<Hospital> findByStatus(String status);

    public List<Hospital> findByStatusNot(String status);

    public Optional<Hospital> findByIdAndStatus(Integer id, String status);

    public List<Hospital> findByNameStartsWithAndStatusNot(String hospital_name, String s);
}
