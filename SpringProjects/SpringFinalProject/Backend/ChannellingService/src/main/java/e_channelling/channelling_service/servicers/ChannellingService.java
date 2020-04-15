package e_channelling.channelling_service.servicers;

import e_channelling.channelling_service.dto.ChannellingDto;
import e_channelling.channelling_service.dto.ChannellingSearchByIdsDto;
import e_channelling.channelling_service.dto.ChannellingSearchDTO;
import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.models.Channelling;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ChannellingService {

    public ResponseDto save(@RequestBody Channelling channelling);

    public ResponseDto update(@RequestBody Channelling channelling);

    public ResponseDto delete(@RequestBody int id, String token, String name);

//    public List<Channelling> search(@RequestBody Channelling channelling);
    public List<ChannellingDto> search(ChannellingSearchDTO channellingSearchDTO);

    public boolean checkChannelling(Channelling channelling);

    public boolean checkDoctorAvailabilityInHospital(Channelling channelling);

    public boolean checkHospital(Integer id);

    public boolean checkDoctor(Integer id);

    public boolean checkAppointments(Integer id);

    public Boolean findByIdAndStatus(Integer id, String status);

    public List<ChannellingDto> findChannellingsByIds(ChannellingSearchByIdsDto channellingSearchByIdsDto);

    List<ChannellingDto> searchByHospital(ChannellingSearchDTO channellingSearchDTO, String name, String token);

    ResponseDto startChannelling(int id, String token, String name);

    ResponseDto finishChannelling(int id, String token, String name);

    Channelling findById(Integer id);

//    public List<Channelling> searchBeforeSave(@RequestBody Channelling channelling);
//
//    public Boolean findById(Integer id);

}
