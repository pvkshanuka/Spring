package lk.e_channeling.hospital_service.servicers;

import lk.e_channeling.hospital_service.dto.ResponseDto;
import lk.e_channeling.hospital_service.models.Hospital;
import lk.e_channeling.hospital_service.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;


    @Override
    public ResponseDto save(Hospital hospital) {
        Hospital save = hospitalRepository.save(hospital);
        if (save.getId().equals(null)) {
            return new ResponseDto(false, "Hospital Save Failed.!");
        } else {
            return new ResponseDto(true, "Hospital Saved Successfully.!");
        }
    }

    @Override
    public ResponseDto update(Hospital hospital) {
        return null;
    }

    @Override
    public ResponseDto delete(int id) {
        return null;
    }

    @Override
    public List<Hospital> search(Hospital hospital) {
        return null;
    }

    @Override
    public List<Hospital> searchAll() {
        return null;
    }
}
