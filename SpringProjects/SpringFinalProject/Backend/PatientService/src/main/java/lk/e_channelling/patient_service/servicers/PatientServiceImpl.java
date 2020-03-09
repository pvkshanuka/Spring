package lk.e_channelling.patient_service.servicers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Appointment;
import lk.e_channelling.patient_service.models.Patient;
import lk.e_channelling.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public ResponseDto save(Patient patient) {
//
//        for (Appointment appointment : patient.getAppointments()) {
//            appointment.setPatient_id(patient.getId());
//        }

        Patient save = patientRepository.save(patient);

        if (save.getId().equals(null)) {
            System.out.println("Patient Save Failed.!");
            return new ResponseDto(false, "Patient Save Failed.!");
        } else {
            System.out.println("Patient Saved Successfully.!");
            return new ResponseDto(true, "Patient Saved Successfully.!");
        }

    }

    @Override
    public ResponseDto update(Patient patient) {

        Optional<Patient> optional = patientRepository.findById(patient.getId());

        if (optional.isPresent()) {
            if (optional.get().getStatus().equals("1")) {
                System.out.println(patient);
                patientRepository.save(patient);
                System.out.println("Patient Updated Successfully.!");
                return new ResponseDto(true, "Patient Updated Successfully.!");
            } else {
                return new ResponseDto(true, "Deleted Patient.!");
            }
        } else {
            System.out.println("Patient Update Failed.!");
            return new ResponseDto(false, "Invalid Patient ID.!");
        }

    }

    @Override
    public ResponseDto delete(int id) {

        Optional<Patient> optional = patientRepository.findById(id);

        if (optional.isPresent()) {
            Patient patient = optional.get();
            patient.setStatus("0");
            patientRepository.save(patient);
            System.out.println("Patient Deleted Successfully.!");
            return new ResponseDto(true, "Patient Deleted Successfully.!");
        } else {
            System.out.println("Patient Delete Failed.!");
            return new ResponseDto(false, "Invalid Patient ID.!");
        }

    }

    @Override
    public List<Patient> search(Patient patient) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("appointments")
                .withIgnoreNullValues();

        Example<Patient> example = Example.of(patient, exampleMatcher);
        return patientRepository.findAll(example);

    }
}
