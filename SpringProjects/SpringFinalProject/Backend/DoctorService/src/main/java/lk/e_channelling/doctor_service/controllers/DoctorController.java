package lk.e_channelling.doctor_service.controllers;

import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import lk.e_channelling.doctor_service.servicers.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Doctor doctor) {

        try {

            doctor.setStatus("1");
//            if (true) {
            if (checkCategories(doctor)) {
                return doctorService.save(doctor);
            } else {
                return new ResponseDto(false, "Invalid Categories.!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Save Failed.!");
        }

    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Doctor doctor) {

        try {

//            if (true) {
            if (checkCategories(doctor)) {
                doctor.setStatus("1");
                return doctorService.update(doctor);
            } else {
                return new ResponseDto(false, "Invalid Categories.!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Update Error.!");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {

        try {

            return doctorService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Doctor> search(@RequestBody Doctor doctor) {
        try {

            doctor.setStatus("1");
            return doctorService.search(doctor);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Doctor test() {
        DoctorCategory category1 = new DoctorCategory();
        category1.setCategoryid(1);
        category1.setStatus("1");

        DoctorCategory category2 = new DoctorCategory();
        category2.setCategoryid(2);
        category2.setStatus("1");

        List<DoctorCategory> doctorCategories = new ArrayList<>();
        doctorCategories.add(category1);
        doctorCategories.add(category2);

        Doctor doctor = new Doctor(1, "doc 1", "1234567890", doctorCategories, "1");

        return doctor;

    }

    @RequestMapping("/searchByCat/")
    public List<Doctor> searchByCat(@RequestBody DoctorCategory doctorCategory){
        try {
            System.out.println("Awaaaaa");
            return doctorService.searchByCategory(doctorCategory);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    public boolean checkCategories(Doctor doctor) {

        if (null == doctor.getDoctorCategories() || doctor.getDoctorCategories().isEmpty()) {
            return true;
        } else {

            List<Integer> doctorCategoryIds = new ArrayList<>();

            doctor.getDoctorCategories().forEach(doctorCategory -> doctorCategoryIds.add(doctorCategory.getCategoryid()));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> httpEntity = new HttpEntity<Object>(doctorCategoryIds, httpHeaders);

            ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://localhost:8030/category/test2", HttpMethod.POST, httpEntity, Boolean.class);

            return responseEntity.getBody();

        }

    }

}
