package e_channelling.channelling_service.servicers;

import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.models.Channelling;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ChannellingService {

    public ResponseDto save(@RequestBody Channelling channelling);

    public ResponseDto update(@RequestBody Channelling channelling);

    public ResponseDto delete(@RequestBody int id);

    public List<Channelling> search(@RequestBody Channelling channelling);

    public boolean checkChannelling(Channelling channelling);

//    public List<Channelling> searchBeforeSave(@RequestBody Channelling channelling);
//
//    public Boolean findById(Integer id);

}
