package lk.e_channelling.appointment_service.servicers;

import lk.e_channelling.appointment_service.dto.ResponseDto;
import lk.e_channelling.appointment_service.models.Appointment;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AppointmentService {

    public ResponseDto save(@RequestBody Appointment appointment);

//    public ResponseDto update(@RequestBody Appointment appointment);

    public ResponseDto delete(@RequestBody int id);

    public List<Appointment> search(@RequestBody Appointment appointment);

    public boolean searchById(int id);

    List<Appointment> findByClientAndChannellingAndStatus(Appointment appointment);

    public boolean checkClient(Integer id);

    public boolean checkChannelling(Integer id);

    public boolean searchByChannellingId(Integer id);

}
