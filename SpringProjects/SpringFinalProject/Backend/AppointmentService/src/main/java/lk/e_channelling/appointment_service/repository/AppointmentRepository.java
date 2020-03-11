package lk.e_channelling.appointment_service.repository;

import lk.e_channelling.appointment_service.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    List<Appointment> findByClientAndChannellingAndStatus(Integer client, Integer channelling,String status);

    List<Appointment> findByChannelling(Integer id);

}
