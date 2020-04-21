package lk.e_channeling.hospital_service.servicers;

import lk.e_channeling.hospital_service.HospitalServiceApplication;
import lk.e_channeling.hospital_service.commonModels.Client;
import lk.e_channeling.hospital_service.dto.HospitalResponseDto;
import lk.e_channeling.hospital_service.dto.ResponseDto;
import lk.e_channeling.hospital_service.exceptions.HospitalException;
import lk.e_channeling.hospital_service.models.Hospital;
import lk.e_channeling.hospital_service.repository.HospitalRepository;
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

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseDto save(Hospital hospital) {

        hospital.setId(null);
        hospital.setStatus("1");

        if (searchAll(hospital).isEmpty()) {

            Hospital save = hospitalRepository.save(hospital);
            if (save.getId().equals(null)) {
                System.out.println("Hospital Save Failed.!");
                return new ResponseDto(false, "Hospital Save Failed.!");
            } else {
                System.out.println("Hospital Saved Successfully.!");
                return new ResponseDto(true, "Hospital Saved Successfully.!");
            }

        } else {
            System.out.println("Hospital Already Added.!");
            return new ResponseDto(false, "Hospital Already Added.!");
        }
    }

    @Override
    public ResponseDto update(Hospital hospital) {

        Optional<Hospital> optional = hospitalRepository.findById(hospital.getId());

        if (optional.isPresent()) {

            Hospital update = optional.get();
            update.setContact(hospital.getContact());
            update.setEmail(hospital.getEmail());
            hospitalRepository.save(update);

            System.out.println("Hospital Updated Successfully.!");
            return new ResponseDto(true, "Hospital Updated Successfully.!");
        } else {
            System.out.println("Hospital Update Failed.!");
            return new ResponseDto(false, "Invalid Hospital ID.!");
        }

    }

    @Override
    public ResponseDto delete(int id) {

        Optional<Hospital> optional = hospitalRepository.findById(id);

        if (optional.isPresent()) {
            Hospital hospital = optional.get();
            hospital.setStatus("0");
            hospitalRepository.save(hospital);
            System.out.println("Hospital Deleted Successfully.!");
            return new ResponseDto(true, "Hospital Deleted Successfully.!");
        } else {
            System.out.println("Hospital Delete Failed.!");
            return new ResponseDto(false, "Invalid Hospital ID.!");
        }

    }

//    @Override
//    public List<Hospital> search(Hospital hospital) {
//        return null;
//    }


    @Override
    public List<Hospital> searchAll(Hospital hospital) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnoreNullValues();

        Example<Hospital> example = Example.of(hospital, exampleMatcher);
        return hospitalRepository.findAll(example);

    }

    @Override
    public List<Hospital> search() {
        return hospitalRepository.findByStatus("1");
    }

    @Override
    public Boolean findByIdAndStatus(Integer id, String status) {

        System.out.println("Hospital Checked");

        Optional<Hospital> optional = hospitalRepository.findByIdAndStatus(id, status);

        return optional.isPresent();

    }

    @Override
    public Hospital findById(Integer id) {
        System.out.println("Hospital Checked");

        return hospitalRepository.findById(id).orElse(null);

    }

    @Override
    public List<HospitalResponseDto> findByNameStartsWith(String hospital_name, String token, String name) {

        System.out.println("findByNameStartsWith");

        try {

            List<Hospital> hospitals = null;
            if (hospital_name == null){
                hospitals = hospitalRepository.findByStatusNot("0");
            }else{
                hospitals = hospitalRepository.findByNameStartsWithAndStatusNot(hospital_name, "0");
            }

            List<HospitalResponseDto> hospitalResponseDtos = new ArrayList<>();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("Authorization", token);

            HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);

            ResponseEntity<Client[]> responseEntity;

            for (Hospital hospital: hospitals) {

                responseEntity = restTemplate.exchange("http://" + HospitalServiceApplication.DOMAIN_CLIENT_SERVICE + "client/findByHospital/" + hospital.getId(), HttpMethod.GET, httpEntity, Client[].class);

                if (null != responseEntity.getBody()){

                    hospitalResponseDtos.add(new HospitalResponseDto(hospital.getId(),hospital.getName(),hospital.getCity(),hospital.getEmail(),hospital.getContact(),responseEntity.getBody(),hospital.getStatus()));

                }

            }

            return hospitalResponseDtos;

        } catch (Exception e) {
            throw new HospitalException("Hospital Search exception occurred in HospitalServiceImpl.findByNameStartsWith", e);
        }
    }
}
