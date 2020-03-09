package lk.e_channelling.doctor_service.controllers;

import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Category;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.servicers.CategoryService;
import lk.e_channelling.doctor_service.servicers.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    CategoryService categoryService;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Doctor doctor) {

        try {

            doctor.setStatus("1");
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
        Category category1 = new Category();
        category1.setId(1);
        category1.setCategory("cat1");
        category1.setStatus("1");

        Category category2 = new Category();
        category2.setId(2);
        category2.setCategory("cat2");
        category2.setStatus("1");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        Doctor doctor = new Doctor(1, "doc 1", "1234567890", categories, "1");

        return doctor;

    }

    public boolean checkCategories(Doctor doctor) {

        if (null == doctor.getCategories() || doctor.getCategories().isEmpty()) {
            return true;
        } else {

            boolean isFound = true;

            List<Category> categories = doctor.getCategories();
            for (Category categorie : categories) {
                if (categoryService.searchById(categorie.getId())) {
                    isFound = false;
                    break;
                }
            }

            return isFound;

        }

    }

}
