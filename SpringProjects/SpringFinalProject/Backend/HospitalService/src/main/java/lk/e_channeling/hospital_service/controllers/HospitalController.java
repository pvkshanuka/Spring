package lk.e_channeling.hospital_service.controllers;

import lk.e_channeling.hospital_service.dto.ResponseDto;
import lk.e_channeling.hospital_service.models.Hospital;
import lk.e_channeling.hospital_service.servicers.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Hospital hospital) {

        try {

            return hospitalService.save(hospital);

        } catch (Exception e) {
            System.out.println("log exception");
            return new ResponseDto(false, "Hospital Save Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Hospital hospital) {
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseDto delete(@RequestBody int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{hospital}")
    public List<Hospital> search(@RequestBody Hospital hospital) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Hospital> searchAll() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Hospital test() {
        return new Hospital(1, "ABC", "DEF", "GHI", 123457890);
    }

}
