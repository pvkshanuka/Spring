package lk.e_channelling.patient_service.repository;

import lk.e_channelling.patient_service.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
}
