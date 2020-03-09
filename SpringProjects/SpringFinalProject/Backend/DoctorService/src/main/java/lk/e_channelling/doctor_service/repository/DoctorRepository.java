package lk.e_channelling.doctor_service.repository;

import lk.e_channelling.doctor_service.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
}
