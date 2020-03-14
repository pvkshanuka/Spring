package lk.e_channelling.doctor_service.controllers;

import lk.e_channelling.doctor_service.exceptions.InvalidArgumentException;
import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import lk.e_channelling.doctor_service.servicers.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @RequestMapping(method = RequestMethod.POST)
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseDto save(@RequestBody Doctor doctor) {

        try {

                return doctorService.save(doctor);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Save Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseDto update(@RequestBody Doctor doctor) {

        try {

            return doctorService.update(doctor);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Doctor Update Error.!");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ExceptionHandler(InvalidArgumentException.class)
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
    @ExceptionHandler(InvalidArgumentException.class)
    public List<Doctor> search(@RequestBody Doctor doctor) {
        try {

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

    @RequestMapping(method = RequestMethod.GET, value = "/searchByCat/{id}")
    @ExceptionHandler(InvalidArgumentException.class)
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

            return doctorService.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return false;
        }
    }

}
