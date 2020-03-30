package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.commonModels.Category;
import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    public ResponseDto save(@RequestBody Doctor doctor);

    public ResponseDto update(@RequestBody Doctor doctor);

    public ResponseDto delete(@RequestBody int id);

    public List<Doctor> search(@RequestBody Doctor doctor);

    public List<Doctor> searchAll();


    //    List<Doctor> searchByCategory(@RequestBody Doctor doctor);
    List<Doctor> searchByCategory(@RequestBody Integer id);

    public Optional<Doctor> findById(Integer id);

    Category[] getCats(Integer id);
}
