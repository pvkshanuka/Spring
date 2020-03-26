package lk.e_channelling.patient_service.servicers;

import lk.e_channelling.patient_service.dto.LoginRequestDto;
import lk.e_channelling.patient_service.dto.LoginResponseDto;
import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Client;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ClientService {

    public ResponseDto save(@RequestBody Client client);

    public ResponseDto update(@RequestBody Client client);

    public ResponseDto delete(@RequestBody int id);

    public List<Client> search(@RequestBody Client client);

    public List<Client> searchBeforeSave(@RequestBody Client client);

    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto);

    public Boolean findById(Integer id);


}
