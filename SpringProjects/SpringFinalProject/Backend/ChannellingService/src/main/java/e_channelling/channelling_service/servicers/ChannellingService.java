package e_channelling.channelling_service.servicers;

import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.models.Channelling;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ChannellingService {

    public ResponseDto save(@RequestBody Channelling channelling);

    public ResponseDto update(@RequestBody Channelling channelling);

    public ResponseDto delete(@RequestBody int id);

    public List<Channelling> search(@RequestBody Channelling channelling);

    public boolean checkChannelling(Channelling channelling);

    public boolean checkHospital(Integer id);

    public boolean checkAppointments(Integer id);

    public Boolean findByIdAndStatus(Integer id, String status);

//    public List<Channelling> searchBeforeSave(@RequestBody Channelling channelling);
//
//    public Boolean findById(Integer id);

}
