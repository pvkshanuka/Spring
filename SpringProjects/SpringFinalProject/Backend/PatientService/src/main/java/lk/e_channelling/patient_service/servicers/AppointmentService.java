package lk.e_channelling.patient_service.servicers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Appointment;
import lk.e_channelling.patient_service.models.Patient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AppointmentService {

    public ResponseDto save(@RequestBody Appointment appointment);

//    public ResponseDto update(@RequestBody Appointment appointment);

    public ResponseDto delete(@RequestBody int id);

    public List<Appointment> search(@RequestBody Appointment appointment);

    public boolean searchById(int id);

    List<Appointment> findByPatientAndChannellingId (Appointment appointment);

}
