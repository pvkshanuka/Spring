package lk.e_channelling.appointment_service.servicers;

import lk.e_channelling.appointment_service.AppointmentServiceApplication;
import lk.e_channelling.appointment_service.commonModels.Channelling;
import lk.e_channelling.appointment_service.dto.*;
import lk.e_channelling.appointment_service.exceptions.AppointmentException;
import lk.e_channelling.appointment_service.models.Appointment;
import lk.e_channelling.appointment_service.repository.AppointmentRepository;
import lk.e_channelling.appointment_service.support.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Validation validation;


    @Override
    public ResponseDto save(Appointment appointment, String token) {

        try {

            if (validation.saveValidator(appointment)) {

                if (checkClient(appointment.getClient(), token)) {

                    appointment.setId(null);

                    if (checkChannelling(appointment.getChannelling())) {

                        appointment.setStatus("1");
                        appointment.setDate(Instant.now());

                        if (findByClientAndChannellingAndStatus(appointment).isEmpty()) {


                            Appointment save = appointmentRepository.save(appointment);
                            if (save.equals(null)) {
                                System.out.println("Appointment Adding Failed.!");
                                return new ResponseDto(false, "Appointment Adding Failed.!");
                            } else {
                                System.out.println("Appointment Added Successfully.!");
                                return new ResponseDto(true, "Appointment Added Successfully.!");
                            }
                        } else {
                            System.out.println("Appointment Already Added.!");
                            return new ResponseDto(false, "Appointment Already Added.!");
                        }

                    } else {
                        System.out.println("Invalid Channelling.!");
                        return new ResponseDto(false, "Invalid Channelling.!");
                    }

                } else {
                    System.out.println("Invalid Client.!");
                    return new ResponseDto(false, "Invalid Client.!");
                }
            } else {
                System.out.println("Invalid Appointment Details.!");
                return new ResponseDto(false, "Invalid Appointment Details.!");
            }

        } catch (
                Exception e) {
            throw new AppointmentException("Appointment saving exception occurred in AppointmentServiceImpl.save", e);
        }

    }

    @Override
    public ResponseDto delete(int id) {
        System.out.println("delete");
        try {

            Optional<Appointment> optional = appointmentRepository.findById(id);

            if (optional.isPresent()) {
                Appointment appointment = optional.get();
                if (appointment.getStatus().equals("1")) {
                    appointment.setStatus("0");
                    appointmentRepository.save(appointment);
                    System.out.println("Appointment Deleted Successfully.!");
                    return new ResponseDto(true, "Appointment Deleted Successfully.!");
                }else{
                    System.out.println("Invalid Appointment to Delete.!");
                    return new ResponseDto(false, "Invalid Appointment to Delete.!");
                }
            } else {
                System.out.println("Appointment Delete Failed.!");
                return new ResponseDto(false, "Invalid Appointment ID.!");
            }
        } catch (Exception e) {
            throw new AppointmentException("Appointment deleting exception occurred in AppointmentServiceImpl.delete", e);
        }
    }

    @Override
    public List<Appointment> search(Appointment appointment) {

        try {

            appointment.setStatus("1");

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                    .withMatcher("patient_id", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                    .withMatcher("channelling_id", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                    .withMatcher("date", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                    .withIgnoreNullValues();

            Example<Appointment> example = Example.of(appointment, exampleMatcher);
            return appointmentRepository.findAll(example);
        } catch (Exception e) {
            throw new AppointmentException("Appointment searching exception occurred in AppointmentServiceImpl.search", e);
        }
    }

    @Override
    public boolean searchById(int id) {
        try {
            Example<Appointment> example = Example.of(new Appointment(id, null, null, null, "1"));
            return appointmentRepository.findAll(example).isEmpty();
        } catch (Exception e) {
            throw new AppointmentException("Appointment searchById exception occurred in AppointmentServiceImpl.searchById", e);
        }
    }

    @Override
    public List<Appointment> findByClientAndChannellingAndStatus(Appointment appointment) {
        try {
            return appointmentRepository.findByClientAndChannellingAndStatus(appointment.getClient(), appointment.getChannelling(), appointment.getStatus());
        } catch (Exception e) {
            throw new AppointmentException("Appointment findByClientAndChannellingAndStatus exception occurred in AppointmentServiceImpl.findByClientAndChannellingAndStatus", e);
        }
    }

    @Override
    public boolean checkClient(Integer id, String token) {
        try {

//            String credentials = AppointmentServiceApplication.OAUTH_CLIENT_ID + ":" + AppointmentServiceApplication.OAUTH_CLIENT_SECRET;
//
//            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("Authorization", token);

            HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);

            ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + AppointmentServiceApplication.DOMAIN_CLIENT_SERVICE + "/client/findBYId/" + id, HttpMethod.GET, httpEntity, Boolean.class);

            return responseEntity.getBody();

        } catch (Exception e) {
            throw new AppointmentException("Appointment checkClient exception occurred in AppointmentServiceImpl.checkClient", e);
        }
    }

    @Override
    public boolean checkChannelling(Integer id) {
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);

            ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + AppointmentServiceApplication.DOMAIN_CHANNELLING_SERVICE + "/channelling/findByIdAndStatus/" + id, HttpMethod.GET, httpEntity, Boolean.class);

            return responseEntity.getBody();

        } catch (Exception e) {
            throw new AppointmentException("Appointment checkChannelling exception occurred in AppointmentServiceImpl.checkChannelling", e);
        }
    }

    @Override
    public boolean searchByChannellingId(Integer id) {
        List<Appointment> byChannelling = appointmentRepository.findByChannelling(id);
        System.out.println(byChannelling);
        return byChannelling.isEmpty();
    }

    @Override
    public List<AppointmentResponseDto> getAppointments(AppointmentSearchDto appointmentSearchDto, String token, String username) {
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("Authorization", token);

            HttpEntity<List<Integer>> httpEntityIntegers;
            HttpEntity<ChannellingSearchByIdsDto> httpEntityChannellingSearch;

            ResponseEntity<ChannellingDto[]> responseEntityCahans;

            HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);
//            ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + AppointmentServiceApplication.DOMAIN_CLIENT_SERVICE + "/client/findByUsername/" + "username", HttpMethod.GET, httpEntity, Boolean.class);

            List<Integer> integersChan = new ArrayList<>();
            List<Appointment> appointments;
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            HashMap<Integer, ChannellingDto> channellingDtoHashMap = new HashMap<>();

//            check client
            if (username.equals(checkClientFromId(appointmentSearchDto.getClient(), httpEntity, token))) {

                ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
                        .withMatcher("client", ExampleMatcher.GenericPropertyMatchers.exact())
//                    .withMatcher("channelling", ExampleMatcher.GenericPropertyMatchers.exact())
//                    .withMatcher("date", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                        .withIgnoreNullValues();

                Example<Appointment> example = Example.of(new Appointment(null, appointmentSearchDto.getClient(), null, null, appointmentSearchDto.getStatus()), exampleMatcher);

                appointments = appointmentRepository.findAll(example);

                appointments.forEach(appointment -> integersChan.add(appointment.getChannelling()));

                httpEntityChannellingSearch = new HttpEntity<>(new ChannellingSearchByIdsDto(integersChan,appointmentSearchDto.getDoctor(),appointmentSearchDto.getDate()),httpHeaders);

                responseEntityCahans = restTemplate.exchange("http://" + AppointmentServiceApplication.DOMAIN_CHANNELLING_SERVICE+ "/channelling/findChannellingsByIds", HttpMethod.POST, httpEntityChannellingSearch, ChannellingDto[].class);


                for (ChannellingDto channellingDto : responseEntityCahans.getBody()) {
                    channellingDtoHashMap.put(channellingDto.getId(),channellingDto);
                }

                appointments.forEach(appointment -> {
                    System.out.println(appointment.getStatus()+" >>>>>>>>>");
                    if (null != channellingDtoHashMap.get(appointment.getChannelling()) && !appointment.getStatus().equals("0")) {
                        appointmentResponseDtos.add(new AppointmentResponseDto(appointment.getId(), channellingDtoHashMap.get(appointment.getChannelling()), appointment.getDate(), appointment.getStatus()));
                    }
                });

                return appointmentResponseDtos;

            } else {
                System.out.println("Invalid User");
                return null;
            }

        } catch (Exception e) {
            throw new AppointmentException("Appointment getAppointments exception occurred in AppointmentServiceImpl.getAppointments", e);
        }
    }

    private String checkClientFromId(Integer id, HttpEntity<String> httpEntity, String token) {

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://" + AppointmentServiceApplication.DOMAIN_CLIENT_SERVICE + "/client/findUsernameById/" + id, HttpMethod.GET, httpEntity, String.class);

        String body = responseEntity.getBody();
        System.out.println(body);
        return body;

    }

}
