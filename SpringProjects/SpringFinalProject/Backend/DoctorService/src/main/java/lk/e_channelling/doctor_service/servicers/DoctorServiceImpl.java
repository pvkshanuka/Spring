package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.Exceptions.DoctorSavingException;
import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import lk.e_channelling.doctor_service.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public ResponseDto save(Doctor doctor) {

        try {

            doctor.setId(null);
            doctor.setStatus("1");

//Checking about doctors that have same name & mobile.
            if (search(doctor).isEmpty()) {

                for (DoctorCategory doctorCategory : doctor.getDoctorCategories()) {
                    doctorCategory.setStatus("1");
                    doctorCategory.setDoctor(doctor);
                }

                Doctor save = doctorRepository.save(doctor);

//Cheacking about doctor is successfully deleted or not
                if (save.equals(null)) {

                    System.out.println("Doctor Save Failed.!");
                    return new ResponseDto(false, "Doctor Save Failed.!");

                } else {

                    System.out.println("Doctor Saved Successfully.!");
                    return new ResponseDto(true, "Doctor Saved Successfully.!");

                }
            } else {

                System.out.println("Doctor Already Added.!");
                return new ResponseDto(true, "Doctor Already Added.!");

            }

        } catch (Exception e) {
            throw new DoctorSavingException("Doctor saving exception occurred in DoctorServiceImpl.save", e);
        }
    }

    @Override
    public ResponseDto update(Doctor doctor) {

        try {

            Optional<Doctor> optional = doctorRepository.findById(doctor.getId());

//Cheacking Doctor is available or not
            if (optional.isPresent()) {

                Doctor doctorfromDB = optional.get();

//Cheacking Doctor is deleted or not
                if (doctorfromDB.getStatus().equals("1")) {

                    for (DoctorCategory doctorCategory : doctor.getDoctorCategories()) {
                        doctorCategory.setStatus("1");
                        doctorCategory.setDoctor(doctor);
                    }

                    doctorRepository.save(doctor);

                    System.out.println("Doctor Updated Successfully.!");

                    return new ResponseDto(true, "Doctor Updated Successfully.!");

                } else {
                    return new ResponseDto(true, "Invalid Doctor.!");
                }

            } else {
                System.out.println("Doctor Update Failed.!");
                return new ResponseDto(false, "Invalid Doctor ID.!");
            }

        } catch (Exception e) {
            throw new DoctorSavingException("Doctor saving exception occurred in DoctorServiceImpl.update", e);
        }

    }

    @Override
    public ResponseDto delete(int id) {

        try {

            Optional<Doctor> optional = doctorRepository.findById(id);


//Cheacking Doctor is available or not
            if (optional.isPresent()) {


//Cheacking Doctor is deleted or not
                if (optional.get().getStatus().equals("1")) {

                    Doctor doctor = optional.get();
                    doctor.setStatus("0");
                    doctorRepository.save(doctor);

                    System.out.println("Doctor Deleted Successfully.!");
                    return new ResponseDto(true, "Doctor Deleted Successfully.!");

                } else {
                    System.out.println("Invalid Doctor ID.!");
                    return new ResponseDto(false, "Invalid Doctor ID.!");
                }
            } else {
                System.out.println("Invalid Doctor ID.!");
                return new ResponseDto(false, "Invalid Doctor ID.!");
            }

        } catch (Exception e) {
            throw new DoctorSavingException("Doctor saving exception occurred in DoctorServiceImpl.delete", e);
        }

    }

    @Override
    public List<Doctor> search(Doctor doctor) {

        try {

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                    .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.startsWith())
                    .withMatcher("doctorCategories", ExampleMatcher.GenericPropertyMatchers.contains())
                    .withIgnoreNullValues();

            AtomicBoolean ok = new AtomicBoolean(false);

            Example<Doctor> example = Example.of(doctor, exampleMatcher);
            List<Doctor> all = doctorRepository.findAll(example);
            List<Doctor> collect = all.stream()
                    .filter(doctor1 -> {
                        ok.set(false);
                        doctor1.getDoctorCategories().forEach(doctorCategory -> {
                            if (doctorCategory.getCategoryid() == 2) {
                                ok.set(true);
                            }
                        });
                        System.out.println("is ok "+ok.get());
                        return ok.get();
                    })
                    .collect(Collectors.toList());
            return collect;

        } catch (Exception e) {
            throw new DoctorSavingException("Doctor saving exception occurred in DoctorServiceImpl.search", e);
        }
    }

    @Override
    public List<Doctor> searchByCategory(DoctorCategory doctorCategory) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(doctorRepository.findByDoctorCategories(doctorCategory));

        return null;
    }
}
