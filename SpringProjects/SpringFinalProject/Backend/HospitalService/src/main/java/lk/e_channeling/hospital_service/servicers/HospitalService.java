package lk.e_channeling.hospital_service.servicers;

import lk.e_channeling.hospital_service.dto.ResponseDto;
import lk.e_channeling.hospital_service.models.Hospital;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface HospitalService {

    public ResponseDto save(@RequestBody Hospital hospital);

    public ResponseDto update(@RequestBody Hospital hospital);

    public ResponseDto delete(@RequestBody int id);

//    public List<Hospital> search(@RequestBody Hospital hospital);

    public List<Hospital> searchAll(@RequestBody Hospital hospital);

    public Boolean findByIdAndStatus(Integer id,String status);

    public Hospital findById(Integer id);

}
