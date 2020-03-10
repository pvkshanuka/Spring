package lk.e_channeling.hospital_service.controllers;

import lk.e_channeling.hospital_service.dto.ResponseDto;
import lk.e_channeling.hospital_service.models.Hospital;
import lk.e_channeling.hospital_service.servicers.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Hospital hospital) {

        try {

            hospital.setStatus("1");
            return hospitalService.save(hospital);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Hospital Save Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Hospital hospital) {
        try {

            return hospitalService.update(hospital);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Hospital Update Failed.!");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {
        try {

            return hospitalService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Hospital Delete Failed.!");
        }
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/id/{hospital}")
//    public List<Hospital> search(@RequestBody Hospital hospital) {
//        return null;
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public List<Hospital> searchAll(@RequestBody Hospital hospital) {
        hospital.setStatus("1");
        return hospitalService.searchAll(hospital);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Hospital test() {
        return new Hospital(1, "ABC", "DEF", "GHI", "123456789","1");
    }

}