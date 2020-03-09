package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Category;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public ResponseDto save(Doctor doctor) {

//        for (Category category: doctor.getCategories()){
//            category.se
//        }

        Doctor save = doctorRepository.save(doctor);

        if (save.getId().equals(null)) {
            System.out.println("Doctor Save Failed.!");
            return new ResponseDto(false, "Doctor Save Failed.!");
        } else {
            System.out.println("Doctor Saved Successfully.!");
            return new ResponseDto(true, "Doctor Saved Successfully.!");
        }
    }

    @Override
    public ResponseDto update(Doctor doctor) {

        Optional<Doctor> optional = doctorRepository.findById(doctor.getId());

        if (optional.isPresent()) {
            if (optional.get().getStatus().equals("1")) {
                System.out.println(doctor);
                doctorRepository.save(doctor);
                System.out.println("Doctor Updated Successfully.!");
                return new ResponseDto(true, "Doctor Updated Successfully.!");
            } else {
                return new ResponseDto(true, "Deleted Doctor.!");
            }
        } else {
            System.out.println("Doctor Update Failed.!");
            return new ResponseDto(false, "Invalid Doctor ID.!");
        }

    }

    @Override
    public ResponseDto delete(int id) {

        Optional<Doctor> optional = doctorRepository.findById(id);

        if (optional.isPresent()) {
            Doctor doctor = optional.get();
            doctor.setStatus("0");
            doctorRepository.save(doctor);
            System.out.println("Doctor Deleted Successfully.!");
            return new ResponseDto(true, "Doctor Deleted Successfully.!");
        } else {
            System.out.println("Doctor Delete Failed.!");
            return new ResponseDto(false, "Invalid Doctor ID.!");
        }

    }

    @Override
    public List<Doctor> search(Doctor doctor) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("categories", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<Doctor> example = Example.of(doctor, exampleMatcher);
        return doctorRepository.findAll(example);

    }
}
