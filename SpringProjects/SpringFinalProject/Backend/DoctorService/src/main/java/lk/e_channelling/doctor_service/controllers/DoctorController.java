package lk.e_channelling.doctor_service.controllers;

import lk.e_channelling.doctor_service.commonModels.Category;
import lk.e_channelling.doctor_service.dto.DoctorDto;
import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import lk.e_channelling.doctor_service.servicers.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PreAuthorize("hasRole('ROLE_operator') or hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Doctor doctor, @RequestHeader("Authorization") String token, Principal principal) {

        try {

            return doctorService.save(doctor, token, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Save Failed.!");
        }

    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Doctor doctor, @RequestHeader("Authorization") String token, Principal principal) {

        try {

            return doctorService.update(doctor, token, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Update Error.!");
        }

    }
    @PreAuthorize("hasRole('ROLE_admin')")
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
    public List<Doctor> searchAll() {
        try {

            return doctorService.searchAll();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.GET, value = "findByNameStartsWith/{doc_name}")
    public List<DoctorDto> findByNameStartsWith(@PathVariable(required = false) String doc_name, @RequestHeader("Authorization") String token, Principal principal) {
        System.out.println("findByNameStartsWith/doc_name");
        try {

            return doctorService.findByNameStartsWith(doc_name, token, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.GET, value = "findByNameStartsWith/")
    public List<DoctorDto> findByNameStartsWithNoName(@RequestHeader("Authorization") String token, Principal principal) {
        System.out.println("findByNameStartsWith/");
        try {

            return doctorService.findByNameStartsWith("", token, principal.getName());

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

    @RequestMapping(method = RequestMethod.GET, value = "/searchByCat/{id}")
    public List<Doctor> searchByCat(@PathVariable Integer id) {
//    public List<Doctor> searchByCat(@RequestBody Doctor doctor) {
        try {

            return doctorService.searchByCategory(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findById/{id}")
    public boolean findById(@PathVariable Integer id) {
//    public List<Doctor> searchByCat(@RequestBody Doctor doctor) {
        try {

            return doctorService.findById(id).isPresent();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/{id}")
    public Doctor find(@PathVariable Integer id) {
//    public List<Doctor> searchByCat(@RequestBody Doctor doctor) {
        try {

            Optional<Doctor> optional = doctorService.findById(id);

            return optional.orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCats/{id}")
    public Category[] getCats(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
//    public List<Doctor> searchByCat(@RequestBody Doctor doctor) {
        try {

            return doctorService.getCats(id, token);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

}
