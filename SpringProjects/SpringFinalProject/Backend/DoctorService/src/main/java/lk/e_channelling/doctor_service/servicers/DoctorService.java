package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DoctorService {

    public ResponseDto save(@RequestBody Doctor doctor);

    public ResponseDto update(@RequestBody Doctor doctor);

    public ResponseDto delete(@RequestBody int id);

    public List<Doctor> search(@RequestBody Doctor doctor);

//    List<Doctor> searchByCategory(@RequestBody Doctor doctor);
    List<Doctor> searchByCategory(@RequestBody Integer id);

    public boolean findById(Integer id);
}
