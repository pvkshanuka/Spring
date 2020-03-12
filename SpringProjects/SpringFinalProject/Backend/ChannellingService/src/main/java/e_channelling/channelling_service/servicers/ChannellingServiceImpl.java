package e_channelling.channelling_service.servicers;

import com.sun.java.browser.dom.DOMAccessException;
import e_channelling.channelling_service.ChannellingServiceApplication;
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

                    if (checkHospital(channelling.getHospital())) {

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
                        System.out.println("Channelling Save Failed (Invalid Hospital).!");
                        return new ResponseDto(false, "Channelling Save Failed (Invalid Hospital).!");
                    }

                } else {
                    System.out.println("Channelling Already Added.!");
                    return new ResponseDto(false, "Channelling Already Added.!");
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

                        if (checkAppointments(channelling.getId())) {


                            Duration gap = Duration.between(channelling.getEndTime(), channelling.getStartTime());

                            System.out.println(">>>>>>>> " + Duration.between(channelling.getEndTime(), channelling.getStartTime()).abs().toMinutes());

                            if (channelling.getStartTime().isBefore(channelling.getEndTime()) && Instant.now().isBefore(channelling.getStartTime()) && gap.abs().toMinutes() >= ChannellingServiceApplication.CHANNELLING_DURATION_MIN && gap.abs().toMinutes() <= ChannellingServiceApplication.CHANNELLING_DURATION_MAX) {

                                channelling.setHospital(channellingDB.getHospital());

                                Channelling save = channellingRepository.save(channelling);

                                if (save.equals(null)) {
                                    System.out.println("Channelling Update Failed.!");
                                    return new ResponseDto(false, "Channelling Update Failed.!");
                                } else {
                                    System.out.println("Channelling Updated Successfully.!");
                                    return new ResponseDto(true, "Channelling Updated Successfully.!");
                                }
                            } else {
                                System.out.println("Invalid Channelling Times.!");
                                return new ResponseDto(false, "Invalid Channelling Times.!");
                            }


                        } else {
                            System.out.println("Unable to Update Channelling (Channelling have Appointments you can delete it Channelling).!");
                            return new ResponseDto(false, "Unable to Update Channelling (Channelling have Appointments you can delete it Channelling).!");
                        }

                    } else {
                        System.out.println("Channelling Already Added.!");
                        return new ResponseDto(false, "Channelling Already Added.!");
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
    public List<Channelling> search(Channelling channelling) {

        channelling.setStatus("1");

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("hospital", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("room", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("price", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("startTime", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("endTime", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("day", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnoreNullValues();

        Example<Channelling> example = Example.of(channelling, exampleMatcher);
        return channellingRepository.findAll(example);

    }


    @Override
    public boolean checkChannelling(Channelling channelling) {
        return channellingRepository.findByHospitalAndRoomAndDayAndStartTimeBetween(channelling.getHospital(), channelling.getRoom(), channelling.getDay(), channelling.getStartTime(), channelling.getEndTime()).isEmpty();
    }

    @Override
    public boolean checkHospital(Integer id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findByIdAndStatus/" + id, HttpMethod.GET, httpEntity, Boolean.class);

        return responseEntity.getBody();

    }

    @Override
    public boolean checkAppointments(Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        System.out.println("Channelling ID : " + id);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/searchByChannellingId/" + id, HttpMethod.GET, httpEntity, Boolean.class);
        return responseEntity.getBody();
    }

    @Override
    public Boolean findByIdAndStatus(Integer id, String status) {
        return channellingRepository.findByIdAndStatus(id,status).isPresent();
    }

}
