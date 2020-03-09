package lk.e_channelling.patient_service.repository;

import lk.e_channelling.patient_service.models.Appointment;
import lk.e_channelling.patient_service.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    List<Appointment> findByPatientAndChannelling(Patient patient,Integer channelling);

}
