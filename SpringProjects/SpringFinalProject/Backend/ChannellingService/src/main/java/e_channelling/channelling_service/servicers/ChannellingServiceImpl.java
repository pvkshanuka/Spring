package e_channelling.channelling_service.servicers;

import com.sun.java.browser.dom.DOMAccessException;
import e_channelling.channelling_service.ChannellingServiceApplication;
import e_channelling.channelling_service.commonModels.Category;
import e_channelling.channelling_service.commonModels.Doctor;
import e_channelling.channelling_service.commonModels.Hospital;
import e_channelling.channelling_service.dto.ChannellingDto;
import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.exception.ChannellingException;
import e_channelling.channelling_service.models.Channelling;
import e_channelling.channelling_service.repository.ChannellingRepository;
import e_channelling.channelling_service.support.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChannellingServiceImpl implements ChannellingService {

    @Autowired
    ChannellingRepository channellingRepository;

    @Autowired
    Validation validation;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseDto save(Channelling channelling) {

        try {
            channelling.setId(null);
            channelling.setStatus("1");

            if (validation.saveValidator(channelling)) {

                if (checkChannelling(channelling)) {

                    if (checkDoctorAvailabilityInHospital(channelling)) {

                        if (checkHospital(channelling.getHospital())) {

                            if (checkDoctor(channelling.getDoctor())) {

                                Duration gap = Duration.between(channelling.getEndTime(), channelling.getStartTime());

//                        System.out.println(">>>>>>>> " + Duration.between(channelling.getEndTime(), channelling.getStartTime()).abs().toMinutes());

                                if (channelling.getStartTime().isBefore(channelling.getEndTime()) && Instant.now().isBefore(channelling.getStartTime()) && gap.abs().toMinutes() >= ChannellingServiceApplication.CHANNELLING_DURATION_MIN && gap.abs().toMinutes() <= ChannellingServiceApplication.CHANNELLING_DURATION_MAX) {
//                    if (channelling.getStartTime().isBefore(channelling.getEndTime()) && Instant.now().isBefore(channelling.getStartTime()) && gap.abs().toMinutes() >= ChannellingServiceApplication.CHANNELLING_DURATION_MIN && gap.abs().toMinutes() <= ChannellingServiceApplication.CHANNELLING_DURATION_MAX) {

                                    Channelling save = channellingRepository.save(channelling);

                                    if (save.equals(null)) {
                                        System.out.println("Channelling Save Failed.!");
                                        return new ResponseDto(false, "Channelling Save Failed.!");
                                    } else {
                                        System.out.println("Channelling Saved Successfully.!");
                                        return new ResponseDto(true, "Channelling Saved Successfully.!");
                                    }
                                } else {
                                    System.out.println("Invalid Channelling Times.!");
                                    return new ResponseDto(false, "Invalid Channelling Times.!");
                                }

                            } else {
                                System.out.println("Channelling Save Failed (Invalid Doctor).!");
                                return new ResponseDto(false, "Channelling Save Failed (Invalid Doctor).!");
                            }

                        } else {
                            System.out.println("Channelling Save Failed (Invalid Hospital).!");
                            return new ResponseDto(false, "Channelling Save Failed (Invalid Hospital).!");
                        }

                    } else {
                        System.out.println("Doctor is not available.!");
                        return new ResponseDto(false, "Doctor is not available.!");
                    }

                } else {
                    System.out.println("Channelling Already Added to Room " + channelling.getRoom() + ".!");
                    return new ResponseDto(false, "Channelling Already Added to Room "+channelling.getRoom()+".!");
                }
            } else {
                System.out.println("Invalid Channelling Details.!");
                return new ResponseDto(false, "Invalid Channelling Details.!");
            }

        } catch (Exception e) {
            throw new ChannellingException("Channelling saving exception occurred in ChannellingServiceImpl.save", e);
        }

    }


    @Override
    public ResponseDto update(Channelling channelling) {

        try {

            if (validation.isInt(channelling.getId() + "") && validation.saveValidator(channelling)) {

                Optional<Channelling> optionalChannelling = channellingRepository.findById(channelling.getId());

                if (optionalChannelling.isPresent()) {

                    Channelling channellingDB = optionalChannelling.get();

                    if (checkChannelling(channelling)) {

                        if (checkDoctorAvailabilityInHospital(channelling)) {

                            if (checkHospital(channelling.getDoctor())) {

                                if (checkAppointments(channelling.getId())) {


                                    Duration gap = Duration.between(channelling.getEndTime(), channelling.getStartTime());

                                    System.out.println(">>>>>>>> " + Duration.between(channelling.getEndTime(), channelling.getStartTime()).abs().toMinutes());

                                    if (channelling.getStartTime().isBefore(channelling.getEndTime()) && Instant.now().isBefore(channelling.getStartTime()) && gap.abs().toMinutes() >= ChannellingServiceApplication.CHANNELLING_DURATION_MIN && gap.abs().toMinutes() <= ChannellingServiceApplication.CHANNELLING_DURATION_MAX) {

                                        channelling.setHospital(channellingDB.getHospital());

                                        Channelling save = channellingRepository.save(channelling);

                                        System.out.println("Channelling Updated Successfully.!");
                                        return new ResponseDto(true, "Channelling Updated Successfully.!");
                                    } else {
                                        System.out.println("Invalid Channelling Times.!");
                                        return new ResponseDto(false, "Invalid Channelling Times.!");
                                    }


                                } else {
                                    System.out.println("Unable to Update Channelling (Channelling have Appointments you can delete it Channelling).!");
                                    return new ResponseDto(false, "Unable to Update Channelling (Channelling have Appointments you can delete it Channelling).!");
                                }

                            } else {
                                System.out.println("Channelling Save Failed (Invalid Doctor).!");
                                return new ResponseDto(false, "Channelling Save Failed (Invalid Doctor).!");
                            }

                        } else {
                            System.out.println("Doctor is not available.!");
                            return new ResponseDto(false, "Doctor is not available.!");
                        }

                    } else {
                        System.out.println("Channelling Already Added to Room " + channelling.getRoom() + ".!");
                        return new ResponseDto(false, "Channelling Already Added to Room "+channelling.getRoom()+".!");
                    }

                } else {
                    System.out.println("Invalid Channelling.!");
                    return new ResponseDto(false, "Invalid Channelling.!");
                }

            } else {
                System.out.println("Invalid Channelling Details.!");
                return new ResponseDto(false, "Invalid Channelling Details.!");
            }

        } catch (Exception e) {
            throw new ChannellingException("Channelling update exception occurred in ChannellingServiceImpl.update", e);
        }

    }

    @Override
    public ResponseDto delete(int id) {

        try {

            Optional<Channelling> optional = channellingRepository.findById(id);

            if (optional.isPresent()) {

                Channelling channelling = optional.get();

                if ("1".equals(channelling.getStatus())) {

                    channelling.setStatus("0");

                    channellingRepository.save(channelling);

                    System.out.println("Channelling Deleted Successfully.!");
                    return new ResponseDto(true, "Channelling Deleted Successfully.!");

                } else {
                    System.out.println("Invalid Channelling.!");
                    return new ResponseDto(false, "Invalid Channelling.!");
                }

            } else {
                System.out.println("Invalid Channelling.!");
                return new ResponseDto(false, "Invalid Channelling.!");
            }


        } catch (Exception e) {
            throw new ChannellingException("Channelling delete exception occurred in ChannellingServiceImpl.delete", e);
        }

    }

    @Override
    public List<ChannellingDto> search() {
//        public List<Channelling> search(Channelling channelling) {

//        channelling.setStatus("1");
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withMatcher("hospital", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withMatcher("room", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
//                .withMatcher("price", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withMatcher("startTime", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
//                .withMatcher("endTime", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
//                .withMatcher("day", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
//                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withIgnoreNullValues();
//
//        Example<Channelling> example = Example.of(channelling, exampleMatcher);
//        return channellingRepository.findAll(example);

        List<Channelling> channellings = channellingRepository.findByStatus("1");

        List<ChannellingDto> channellingDtos = new ArrayList<>();

        ChannellingDto channellingDto;

        HttpHeaders httpHeaders;

        HttpEntity<String> httpEntityString;
        HttpEntity<List<Integer>> httpEntityIntegers;

        ResponseEntity<Doctor> responseEntityDoctor;
        ResponseEntity<Category[]> responseEntityCats;
        ResponseEntity<Hospital> responseEntityHos;

        Date dateToday = new Date();

        List<Integer> integersCat = new ArrayList<>();

        for (Channelling channelling : channellings) {

            if (Date.from(channelling.getStartTime()).after(dateToday)) {


            System.out.println(channelling.getId() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            channellingDto = new ChannellingDto();

            System.out.println(integersCat.size() + " integet size >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


            integersCat.removeAll(integersCat);
            System.out.println(integersCat.size() + " integet size >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            httpHeaders = new HttpHeaders();
            httpEntityString = new HttpEntity<>("", httpHeaders);

            responseEntityDoctor = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_DOCTOR_SERVICE + "/doctor/find/" + channelling.getDoctor(), HttpMethod.GET, httpEntityString, Doctor.class);

            if (null != responseEntityDoctor.getBody()) {


                channellingDto.setDoctor(responseEntityDoctor.getBody());
                channellingDto.getDoctor().setContact("");

                channellingDto.getDoctor().getDoctorCategories().forEach(doctorCategory -> integersCat.add(doctorCategory.getCategoryid()));

                System.out.println("Doctor Id = " + channellingDto.getDoctor().getId());

                System.out.println(integersCat);

                httpHeaders = new HttpHeaders();
                httpEntityIntegers = new HttpEntity<>(integersCat, httpHeaders);

                responseEntityCats = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CATGORY_SERVICE + "/category/searchByIds", HttpMethod.POST, httpEntityIntegers, Category[].class);

                if (null != responseEntityCats.getBody()) {

                    System.out.println(responseEntityCats.getBody());

                    channellingDto.setCategories(responseEntityCats.getBody());

                    httpHeaders = new HttpHeaders();
                    httpEntityString = new HttpEntity<>("", httpHeaders);

                    System.out.println("Hospital ID - " + channelling.getHospital());

                    responseEntityHos = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findById/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Hospital.class);

                    if (null != responseEntityCats.getBody()) {

                        System.out.println(responseEntityHos.getBody());
                        channellingDto.setHospital(responseEntityHos.getBody());

                        channellingDto.setId(channelling.getId());
                        channellingDto.setEndTime(Date.from(channelling.getEndTime()));
                        channellingDto.setPrice(channelling.getPrice());
                        channellingDto.setRoom(channelling.getRoom());
                        channellingDto.setStartTime(Date.from(channelling.getStartTime()));
                        channellingDto.setStatus(channelling.getStatus());

                        channellingDtos.add(channellingDto);

                    } else {
                        throw new ChannellingException("Channelling find(Hospital) exception occurred in ChannellingServiceImpl.find", null);
                    }

                } else {
                    throw new ChannellingException("Channelling find(Category) exception occurred in ChannellingServiceImpl.find", null);
                }
            } else {
                throw new ChannellingException("Channelling find(Doctor) exception occurred in ChannellingServiceImpl.find", null);
            }

        }

        }

        return channellingDtos;
        
    }


    @Override
    public boolean checkChannelling(Channelling channelling) {

        return channellingRepository.findByHospitalAndRoomAndStartTimeBetween(channelling.getHospital(), channelling.getRoom(), channelling.getStartTime(), channelling.getEndTime()).isEmpty();
    }

    @Override
    public boolean checkDoctorAvailabilityInHospital(Channelling channelling) {
        return channellingRepository.findByHospitalAndDoctorAndStartTimeBetween(channelling.getHospital(), channelling.getDoctor(), channelling.getStartTime(), channelling.getEndTime()).isEmpty();
    }

    @Override
    public boolean checkHospital(Integer id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findByIdAndStatus/" + id, HttpMethod.GET, httpEntity, Boolean.class);

        if(null != responseEntity.getBody()) {
            return responseEntity.getBody();
        }else{
            return false;
        }

    }

    @Override
    public boolean checkDoctor(Integer id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_DOCTOR_SERVICE + "/doctor/findById/" + id, HttpMethod.GET, httpEntity, Boolean.class);

        if(null != responseEntity.getBody()) {
            return responseEntity.getBody();
        }else{
            return false;
        }

    }

    @Override
    public boolean checkAppointments(Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        System.out.println("Channelling ID : " + id);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/searchByChannellingId/" + id, HttpMethod.GET, httpEntity, Boolean.class);
        if(null != responseEntity.getBody()) {
            return responseEntity.getBody();
        }else{
            return false;
        }
    }

    @Override
    public Boolean findByIdAndStatus(Integer id, String status) {
        return channellingRepository.findByIdAndStatus(id, status).isPresent();
    }

}
