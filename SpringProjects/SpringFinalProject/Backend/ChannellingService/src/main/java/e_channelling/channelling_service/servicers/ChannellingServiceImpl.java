package e_channelling.channelling_service.servicers;

import com.sun.java.browser.dom.DOMAccessException;
import e_channelling.channelling_service.ChannellingServiceApplication;
import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.models.Channelling;
import e_channelling.channelling_service.repository.ChannellingRepository;
import e_channelling.channelling_service.support.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

        channelling.setId(null);
        channelling.setStatus("1");

        if (validation.saveValidator(channelling)) {

            if (checkChannelling(channelling)) {

                Duration gap = Duration.between(channelling.getEndTime(), channelling.getStartTime());

                System.out.println(">>>>>>>> " + Duration.between(channelling.getEndTime(), channelling.getStartTime()).abs().toMinutes());

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
                System.out.println("Channelling Already Added.!");
                return new ResponseDto(false, "Channelling Already Added.!");
            }
        } else {
            System.out.println("Invalid Channelling Details.!");
            return new ResponseDto(false, "Invalid Channelling Details.!");
        }

    }


    @Override
    public ResponseDto update(Channelling channelling) {

        if (validation.isInt(channelling.getId() + "") && validation.saveValidator(channelling)) {

            Optional<Channelling> optionalChannelling = channellingRepository.findById(channelling.getId());

            if (optionalChannelling.isPresent()) {

                channelling.setHospital(optionalChannelling.get().getHospital());

                if (checkChannelling(channelling)) {

                    HttpHeaders httpHeaders = new HttpHeaders();
                    HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
                    System.out.println("Channelling ID : " + channelling.getId());
                    ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/searchByChannellingId/" + channelling.getId(), HttpMethod.GET, httpEntity, Boolean.class);

                    if (responseEntity.getBody()) {


                        Duration gap = Duration.between(channelling.getEndTime(), channelling.getStartTime());

                        System.out.println(">>>>>>>> " + Duration.between(channelling.getEndTime(), channelling.getStartTime()).abs().toMinutes());

                        if (channelling.getStartTime().isBefore(channelling.getEndTime()) && Instant.now().isBefore(channelling.getStartTime()) && gap.abs().toMinutes() >= ChannellingServiceApplication.CHANNELLING_DURATION_MIN && gap.abs().toMinutes() <= ChannellingServiceApplication.CHANNELLING_DURATION_MAX) {

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

    }

    @Override
    public ResponseDto delete(int id) {
        return null;
    }

    @Override
    public List<Channelling> search(Channelling channelling) {
        return null;
    }


    @Override
    public boolean checkChannelling(Channelling channelling) {
        return channellingRepository.findByHospitalAndRoomAndDayAndStartTimeBetween(channelling.getHospital(), channelling.getRoom(), channelling.getDay(), channelling.getStartTime(), channelling.getEndTime()).isEmpty();
    }

}
