package lk.e_channelling.patient_service.servicers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Patient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PatientService {

    public ResponseDto save(@RequestBody Patient patient);

    public ResponseDto update(@RequestBody Patient patient);

    public ResponseDto delete(@RequestBody int id);

    public List<Patient> search(@RequestBody Patient patient);


}
