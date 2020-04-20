package lk.e_channeling.hospital_service.controllers;

import lk.e_channeling.hospital_service.dto.HospitalResponseDto;
import lk.e_channeling.hospital_service.dto.ResponseDto;
import lk.e_channeling.hospital_service.models.Hospital;
import lk.e_channeling.hospital_service.servicers.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @PreAuthorize("hasRole('ROLE_admin')")
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

    @PreAuthorize("hasRole('ROLE_admin')")
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

    @Transactional
    @PreAuthorize("hasRole('ROLE_admin')")
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Hospital> search() {
        return hospitalService.search();
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.GET, value = "/findByNameStartsWith/{hospital_name}")
    public List<HospitalResponseDto> findByNameStartsWith(@PathVariable(required = false) String hospital_name, @RequestHeader("Authorization") String token, Principal principal) {
        return hospitalService.findByNameStartsWith(hospital_name,token,principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.GET, value = "/findByNameStartsWith/")
    public List<HospitalResponseDto> findByStatus(@RequestHeader("Authorization") String token, Principal principal) {
        return hospitalService.findByNameStartsWith(null,token,principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Hospital test() {
        return new Hospital(1, "ABC", "DEF", "GHI", "123456789","1");
    }

    @RequestMapping(method = RequestMethod.GET,value = "/findByIdAndStatus/{id}")
    public Boolean findByIdAndStatus(@PathVariable Integer id){
        return hospitalService.findByIdAndStatus(id,"1");
    }

    @RequestMapping(method = RequestMethod.GET,value = "/findById/{id}")
    public Hospital findById(@PathVariable Integer id){

        System.out.println(id);
        Hospital byId = hospitalService.findById(id);
        System.out.println(byId);
        return byId;
    }

}