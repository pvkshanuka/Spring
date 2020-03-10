package lk.e_channelling.appointment_service.servicers;

import lk.e_channelling.appointment_service.dto.ResponseDto;
import lk.e_channelling.appointment_service.models.Appointment;
import lk.e_channelling.appointment_service.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public ResponseDto save(Appointment appointment) {

        if (findByClientAndChannellingAndStatus(appointment).isEmpty()) {

            Appointment save = appointmentRepository.save(appointment);
            if (save.equals(null)) {
                System.out.println("Appointment Adding Failed.!");
                return new ResponseDto(false, "Appointment Adding Failed.!");
            } else {
                System.out.println("Appointment Added Successfully.!");
                return new ResponseDto(true, "Appointment Added Successfully.!");
            }
        }else{
            System.out.println("Appointment Already Added.!");
            return new ResponseDto(true, "Appointment Already Added.!");
        }
    }

    @Override
    public ResponseDto delete(int id) {

        Optional<Appointment> optional = appointmentRepository.findById(id);

        if (optional.isPresent()) {
            Appointment appointment = optional.get();
            appointment.setStatus("0");
            appointmentRepository.save(appointment);
            System.out.println("Appointment Deleted Successfully.!");
            return new ResponseDto(true, "Appointment Deleted Successfully.!");
        } else {
            System.out.println("Appointment Delete Failed.!");
            return new ResponseDto(false, "Invalid Appointment ID.!");
        }

    }

    @Override
    public List<Appointment> search(Appointment appointment) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("patient_id", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("channelling_id", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("date", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withIgnoreNullValues();

        Example<Appointment> example = Example.of(appointment, exampleMatcher);
        return appointmentRepository.findAll(example);

    }

    @Override
    public boolean searchById(int id) {

        Example<Appointment> example = Example.of(new Appointment(id,null,null,null,"1"));
        return appointmentRepository.findAll(example).isEmpty();

    }

    @Override
    public List<Appointment> findByClientAndChannellingAndStatus(Appointment appointment) {
        return appointmentRepository.findByClientAndChannellingAndStatus(appointment.getClient(),appointment.getChannelling(),appointment.getStatus());
    }
}
