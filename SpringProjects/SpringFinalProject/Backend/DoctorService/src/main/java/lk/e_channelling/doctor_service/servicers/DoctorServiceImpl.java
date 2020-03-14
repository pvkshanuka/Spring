package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.DoctorServiceApplication;
import lk.e_channelling.doctor_service.exceptions.DoctorException;
import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import lk.e_channelling.doctor_service.repository.DoctorRepository;
import lk.e_channelling.doctor_service.support.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Validation validation;

    @Override
    public ResponseDto save(Doctor doctor) {

        try {
            doctor.setStatus("1");
            doctor.setId(null);

            if (validation.doctorSaveValidator(doctor)) {

                doctor.setId(null);
                doctor.setStatus("1");

//Cheacking categories are available or not
                if (checkCategories(doctor)) {

//Checking about doctors that have same mobile.
                    if (doctorRepository.findByContact(doctor.getContact()).isEmpty()) {

                        if (null != doctor.getDoctorCategories()) {
                            for (DoctorCategory doctorCategory : doctor.getDoctorCategories()) {
                                doctorCategory.setStatus("1");
                                doctorCategory.setDoctor(doctor);
                            }
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

                } else {
                    return new ResponseDto(false, "Invalid Categories.!");
                }

            } else {
                return new ResponseDto(false, "Invalid Doctor Details.!");
            }

        } catch (Exception e) {
            throw new DoctorException("Doctor saving exception occurred in DoctorServiceImpl.save", e);
        }
    }

    @Override
    public ResponseDto update(Doctor doctor) {

        try {

            if (validation.doctorSaveValidator(doctor)) {

//Cheacking categories are available or not
                if (checkCategories(doctor)) {

                    doctor.setStatus("1");

                    Optional<Doctor> optional = doctorRepository.findById(doctor.getId());

//Cheacking Doctor is available or not
                    if (optional.isPresent()) {

                        Doctor doctorfromDB = optional.get();

//Cheacking Doctor is deleted or not
                        if (doctorfromDB.getStatus().equals("1")) {

                            if (null != doctor.getDoctorCategories()) {

                                boolean isFound = false;

                                List<DoctorCategory> doctorCategories = new ArrayList<>();

                                for (DoctorCategory doctorCategory : doctor.getDoctorCategories()) {

                                    for (DoctorCategory doctorCategoryDB : doctorfromDB.getDoctorCategories()) {

//Cheacking if recived doctor's categorys equal to db loaded doctor's categorys
                                        if (doctorCategory.getCategoryid() == doctorCategoryDB.getCategoryid()) {

                                            //Cheacking if db loaded doctor's category status equal 1
                                            if (doctorCategoryDB.getStatus().equals("1")) {
                                                isFound = true;
                                                break;
                                            }

                                        }
                                    }

                                    if (!isFound) {
                                        doctorCategory.setStatus("1");
                                        doctorCategory.setDoctor(doctor);
                                        doctorCategories.add(doctorCategory);
                                    }
                                    isFound = false;
                                }

                                doctor.setDoctorCategories(doctorCategories);
                            }

                            doctor.setContact(doctorfromDB.getContact());

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

                } else {
                    return new ResponseDto(false, "Invalid Categories.!");
                }

            } else {
                return new ResponseDto(false, "Invalid Doctor Details.!");
            }

        } catch (Exception e) {
            throw new DoctorException("Doctor updating exception occurred in DoctorServiceImpl.update", e);
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
            throw new DoctorException("Doctor deleting exception occurred in DoctorServiceImpl.delete", e);
        }

    }

    @Override
    public List<Doctor> search(Doctor doctor) {

        try {

            doctor.setStatus("1");

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                    .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.startsWith())
                    .withMatcher("doctorCategories", ExampleMatcher.GenericPropertyMatchers.contains())
                    .withIgnoreNullValues();

            AtomicBoolean ok = new AtomicBoolean(false);

            Example<Doctor> example = Example.of(doctor, exampleMatcher);
            List<Doctor> all = doctorRepository.findAll(example);
            return all;

        } catch (Exception e) {
            throw new DoctorException("Doctor searching exception occurred in DoctorServiceImpl.search", e);
        }
    }

    @Override
    public List<Doctor> searchByCategory(Integer id) {
//    public List<Doctor> searchByCategory(Doctor doctor) {

        try {

//            doctor.setStatus("1");
//
//            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
//                    .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                    .withMatcher("doctorCategories", ExampleMatcher.GenericPropertyMatchers.contains())
//                    .withIgnoreNullValues();
//
//            AtomicBoolean ok = new AtomicBoolean(false);
//
//            Example<Doctor> example = Example.of(doctor, exampleMatcher);
//            List<Doctor> all = doctorRepository.findAll(example);
//            List<Doctor> collect = all.stream()
//                    .filter(doctor1 -> {
//                        ok.set(false);
//                        doctor1.getDoctorCategories().forEach(doctorCategory -> {
//                            if (doctorCategory.getCategoryid() == doctor.getDoctorCategories().get(0).getCategoryid()) {
//                                ok.set(true);
//                            }
//                        });
//                        System.out.println("is ok " + ok.get());
//                        return ok.get();
//                    })
//                    .collect(Collectors.toList());
//            return collect;

            return doctorRepository.findAllDoctorsByCategory(id);

        } catch (Exception e) {
            throw new DoctorException("Doctor searchByCategory exception occurred in DoctorServiceImpl.searchByCategory", e);
        }
    }

    @Override
    public boolean findById(Integer id) {
        try {
            System.out.println("Doctor Check");
            return doctorRepository.findById(id).isPresent();

        } catch (Exception e) {
            throw new DoctorException("Doctor searchByCategory exception occurred in DoctorServiceImpl.searchByCategory", e);
        }
    }

    public boolean checkCategories(Doctor doctor) {

        try {

            if (null == doctor.getDoctorCategories() || doctor.getDoctorCategories().isEmpty()) {
                return true;
            } else {

                List<Integer> doctorCategoryIds = new ArrayList<>();

                doctor.getDoctorCategories().forEach(doctorCategory -> doctorCategoryIds.add(doctorCategory.getCategoryid()));

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<Object> httpEntity = new HttpEntity<Object>(doctorCategoryIds, httpHeaders);

                ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + DoctorServiceApplication.DOMAIN_CATEGORY_SERVICE + "/test2", HttpMethod.POST, httpEntity, Boolean.class);

                return responseEntity.getBody();

            }

        } catch (Exception e) {
            throw new DoctorException("Doctor checkCategories exception occurred in DoctorServiceImpl.searchByCategory", e);
        }


    }

}
