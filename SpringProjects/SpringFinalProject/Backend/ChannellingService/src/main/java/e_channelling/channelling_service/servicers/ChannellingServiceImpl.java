package e_channelling.channelling_service.servicers;

import e_channelling.channelling_service.ChannellingServiceApplication;
import e_channelling.channelling_service.commonModels.Appointment;
import e_channelling.channelling_service.commonModels.Category;
import e_channelling.channelling_service.commonModels.Doctor;
import e_channelling.channelling_service.commonModels.Hospital;
import e_channelling.channelling_service.dto.ChannellingDto;
import e_channelling.channelling_service.dto.ChannellingSearchByIdsDto;
import e_channelling.channelling_service.dto.ChannellingSearchDTO;
import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.exception.ChannellingException;
import e_channelling.channelling_service.models.Channelling;
import e_channelling.channelling_service.repository.ChannellingRepository;
import e_channelling.channelling_service.support.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.*;

@Service
public class ChannellingServiceImpl implements ChannellingService {

    @Autowired
    ChannellingRepository channellingRepository;

    @Autowired
    Validation validation;
    private ChannellingDto channellingDto;

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
                    return new ResponseDto(false, "Channelling Already Added to Room " + channelling.getRoom() + ".!");
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
                        return new ResponseDto(false, "Channelling Already Added to Room " + channelling.getRoom() + ".!");
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
    public ResponseDto delete(int id, String token, String name) {

        try {

            Optional<Channelling> optional = channellingRepository.findById(id);

            if (optional.isPresent()) {

                Channelling channelling = optional.get();

                if ("1".equals(channelling.getStatus())) {

                    HttpHeaders httpHeaders;

                    HttpEntity<String> httpEntityString;

                    httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.add("Authorization", token);

                    httpEntityString = new HttpEntity<>("", httpHeaders);

                    ResponseEntity<Boolean> responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CLIENT_SERVICE + "client/findByEmailAndHospital/" + name + "/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Boolean.class);

                    if (responseEntityBoolean.getBody()) {

                        ResponseEntity<Boolean> responseEntityAppo = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/updateStatusByChannelling/" + channelling.getId() + "/" + 5, HttpMethod.PUT, httpEntityString, Boolean.class);

                        if (responseEntityAppo.getBody()) {

                            channelling.setStatus("0");

                            channellingRepository.save(channelling);

                            System.out.println("Channelling Deleted Successfully.!");
                            return new ResponseDto(true, "Channelling Deleted Successfully.!");

                        } else {
                            System.out.println("Appointment Update Failed.!");
                            return new ResponseDto(false, "Appointment Update Failed.!");
                        }
                    } else {
                        System.out.println("Invalid Channelling to Delete.!");
                        return new ResponseDto(false, "Invalid Channelling to Delete.!");
                    }


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
    public List<ChannellingDto> search(ChannellingSearchDTO channellingSearchDTO) {
        System.out.println(channellingSearchDTO);
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

//        System.out.println(Date.from(channellingSearchDTO.getDate()));
//
//        System.out.println(Date.from(channellingSearchDTO.getDate().plusSeconds(60*60*24)));

        List<Channelling> channellings = null;

        if (null == channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatus("1");
        } else if (null != channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndHospitalAndDoctor("1", channellingSearchDTO.getHospital(), channellingSearchDTO.getDoctor());

        } else if (null == channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndDoctorAndStartTimeBetween("1", channellingSearchDTO.getDoctor(), channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));


        } else if (null != channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndHospitalAndStartTimeBetween("1", channellingSearchDTO.getHospital(), channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));


        } else if (null != channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndHospital("1", channellingSearchDTO.getHospital());


        } else if (null == channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndDoctor("1", channellingSearchDTO.getDoctor());


        } else if (null == channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndStartTimeBetween("1", channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));


        } else if (null != channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
            channellings = channellingRepository.findByStatusAndHospitalAndDoctorAndStartTimeBetween("1", channellingSearchDTO.getHospital(), channellingSearchDTO.getDoctor(), channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));

        }

//        List<Channelling> channellings = channellingRepository.findByStatusAndHospitalAndDoctorAndStartTimeBetween("1",channellingSearchDTO.getHospital(),channellingSearchDTO.getDoctor(), LocalDate.from(channellingSearchDTO.getDate()).atStartOfDay(ZoneId.systemDefault()).toInstant(),LocalDate.from(channellingSearchDTO.getDate()).plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
//        List<Channelling> channellings = channellingRepository.findByStatusAndHospitalAndDoctorAndStartTimeBetween("1",channellingSearchDTO.getHospital(),channellingSearchDTO.getDoctor(), null,null);

        System.out.println(channellings.size());

        channellings.forEach(channelling -> System.out.println(channelling));

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


//            System.out.println(channelling.getId() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                channellingDto = new ChannellingDto();

//            System.out.println(integersCat.size() + " integet size >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


                integersCat.removeAll(integersCat);
//            System.out.println(integersCat.size() + " integet size >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                httpHeaders = new HttpHeaders();
                httpEntityString = new HttpEntity<>("", httpHeaders);

                responseEntityDoctor = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_DOCTOR_SERVICE + "/doctor/find/" + channelling.getDoctor(), HttpMethod.GET, httpEntityString, Doctor.class);

                if (null != responseEntityDoctor.getBody()) {


                    channellingDto.setDoctor(responseEntityDoctor.getBody());
                    channellingDto.getDoctor().setContact("");

                    channellingDto.getDoctor().getDoctorCategories().forEach(doctorCategory -> integersCat.add(doctorCategory.getCategoryid()));

                    if (null == channellingSearchDTO.getCategory() || integersCat.contains(channellingSearchDTO.getCategory())) {

//                System.out.println("Doctor Id = " + channellingDto.getDoctor().getId());

//                System.out.println(integersCat);

                        httpHeaders = new HttpHeaders();
                        httpEntityIntegers = new HttpEntity<>(integersCat, httpHeaders);

                        responseEntityCats = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CATGORY_SERVICE + "/category/searchByIds", HttpMethod.POST, httpEntityIntegers, Category[].class);

                        if (null != responseEntityCats.getBody()) {

//                    System.out.println(responseEntityCats.getBody());

                            channellingDto.setCategories(responseEntityCats.getBody());

                            httpHeaders = new HttpHeaders();
                            httpEntityString = new HttpEntity<>("", httpHeaders);

//                    System.out.println("Hospital ID - " + channelling.getHospital());

                            responseEntityHos = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findById/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Hospital.class);

                            if (null != responseEntityCats.getBody()) {

//                        System.out.println(responseEntityHos.getBody());
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

        if (null != responseEntity.getBody()) {
            return responseEntity.getBody();
        } else {
            return false;
        }

    }

    @Override
    public boolean checkDoctor(Integer id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_DOCTOR_SERVICE + "/doctor/findById/" + id, HttpMethod.GET, httpEntity, Boolean.class);

        if (null != responseEntity.getBody()) {
            return responseEntity.getBody();
        } else {
            return false;
        }

    }

    @Override
    public boolean checkAppointments(Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        System.out.println("Channelling ID : " + id);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/searchByChannellingId/" + id, HttpMethod.GET, httpEntity, Boolean.class);
        if (null != responseEntity.getBody()) {
            return responseEntity.getBody();
        } else {
            return false;
        }
    }

    @Override
    public Boolean findByIdAndStatus(Integer id, String status) {
        return channellingRepository.findByIdAndStatus(id, status).isPresent();
    }

    @Override
    public List<ChannellingDto> findChannellingsByIds(ChannellingSearchByIdsDto channellingSearchByIdsDto) {

        List<Channelling> channellings = null;
        List<Channelling> channellingsFiltered = new ArrayList<>();
        List<ChannellingDto> channellingDtos = new ArrayList<>();

        ChannellingDto channellingDto;

        channellings = channellingRepository.findAllById(channellingSearchByIdsDto.getIds());

        System.out.println(channellings);

        HttpHeaders httpHeaders;

        HttpEntity<String> httpEntityString;
        HttpEntity<List<Integer>> httpEntityIntegers;

        ResponseEntity<Doctor> responseEntityDoctor;
        ResponseEntity<Category[]> responseEntityCats;
        ResponseEntity<Hospital> responseEntityHos;

        List<Integer> integersCat = new ArrayList<>();

        if (null != channellingSearchByIdsDto.getDoctor()) {
            channellings.forEach(channelling -> {
                if (channelling.getDoctor() == channellingSearchByIdsDto.getDoctor()) {
                    channellingsFiltered.add(channelling);
                }
            });
            channellings.removeAll(channellings);
            channellings.addAll(channellingsFiltered);
            channellingsFiltered.removeAll(channellingsFiltered);
        }


//        if (!channellingsFiltered.isEmpty()) {
//            channellings.removeAll(channellings);
//            channellings.addAll(channellingsFiltered);
//            channellingsFiltered.removeAll(channellingsFiltered);
//        }

        System.out.println(channellings);

        if (null != channellingSearchByIdsDto.getDate()) {
            channellings.forEach(channelling -> {
                if (channelling.getStartTime().isAfter(channellingSearchByIdsDto.getDate()) && channelling.getStartTime().isBefore(channellingSearchByIdsDto.getDate().plusSeconds(60 * 60 * 24))) {
                    channellingsFiltered.add(channelling);
                }
            });
            channellings.removeAll(channellings);
            channellings.addAll(channellingsFiltered);
            channellingsFiltered.removeAll(channellingsFiltered);
        }

//        if (!channellingsFiltered.isEmpty()) {
//            channellings.removeAll(channellings);
//            channellings.addAll(channellingsFiltered);
//            channellingsFiltered.removeAll(channellingsFiltered);
//        }

        System.out.println(channellings);

        if (!channellings.isEmpty()) {


            for (Channelling channelling : channellings) {


                channellingDto = new ChannellingDto();

                integersCat.removeAll(integersCat);
//            System.out.println(integersCat.size() + " integet size >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                httpHeaders = new HttpHeaders();
                httpEntityString = new HttpEntity<>("", httpHeaders);

                responseEntityDoctor = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_DOCTOR_SERVICE + "/doctor/find/" + channelling.getDoctor(), HttpMethod.GET, httpEntityString, Doctor.class);

                if (null != responseEntityDoctor.getBody()) {


                    channellingDto.setDoctor(responseEntityDoctor.getBody());
                    channellingDto.getDoctor().setContact("");

                    channellingDto.getDoctor().getDoctorCategories().forEach(doctorCategory -> integersCat.add(doctorCategory.getCategoryid()));


//                System.out.println("Doctor Id = " + channellingDto.getDoctor().getId());

//                System.out.println(integersCat);

                    httpHeaders = new HttpHeaders();
                    httpEntityIntegers = new HttpEntity<>(integersCat, httpHeaders);

                    responseEntityCats = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CATGORY_SERVICE + "/category/searchByIds", HttpMethod.POST, httpEntityIntegers, Category[].class);

                    if (null != responseEntityCats.getBody()) {

//                    System.out.println(responseEntityCats.getBody());

                        channellingDto.setCategories(responseEntityCats.getBody());

                        httpHeaders = new HttpHeaders();
                        httpEntityString = new HttpEntity<>("", httpHeaders);

//                    System.out.println("Hospital ID - " + channelling.getHospital());

                        responseEntityHos = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findById/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Hospital.class);

                        if (null != responseEntityCats.getBody()) {

//                        System.out.println(responseEntityHos.getBody());
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

            System.out.println(channellingDtos);
            return channellingDtos;

        } else {
            return null;
        }
    }

    @Override
    public List<ChannellingDto> searchByHospital(ChannellingSearchDTO channellingSearchDTO, String name, String token) {
        System.out.println(channellingSearchDTO);

        List<Channelling> channellings = null;

        HttpHeaders httpHeaders;

        HttpEntity<String> httpEntityString;

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", token);

        httpEntityString = new HttpEntity<>("", httpHeaders);

        ResponseEntity<Boolean> responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CLIENT_SERVICE + "client/findByEmailAndHospital/" + name + "/" + channellingSearchDTO.getHospital(), HttpMethod.GET, httpEntityString, Boolean.class);

        if (responseEntityBoolean.getBody()) {


            if (null == channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNot("0");
            } else if (null != channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndHospitalAndDoctor("0", channellingSearchDTO.getHospital(), channellingSearchDTO.getDoctor());

            } else if (null == channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndDoctorAndStartTimeBetween("0", channellingSearchDTO.getDoctor(), channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));


            } else if (null != channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndHospitalAndStartTimeBetween("0", channellingSearchDTO.getHospital(), channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));


            } else if (null != channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndHospital("0", channellingSearchDTO.getHospital());


            } else if (null == channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null == channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndDoctor("0", channellingSearchDTO.getDoctor());


            } else if (null == channellingSearchDTO.getHospital() && null == channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndStartTimeBetween("0", channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));


            } else if (null != channellingSearchDTO.getHospital() && null != channellingSearchDTO.getDoctor() && null != channellingSearchDTO.getDate()) {
                channellings = channellingRepository.findByStatusNotAndHospitalAndDoctorAndStartTimeBetween("0", channellingSearchDTO.getHospital(), channellingSearchDTO.getDoctor(), channellingSearchDTO.getDate(), channellingSearchDTO.getDate().plusSeconds(60 * 60 * 24));

            }

            System.out.println(channellings.size());

//            channellings.forEach(channelling -> System.out.println(channelling));

            List<ChannellingDto> channellingDtos = new ArrayList<>();

            ChannellingDto channellingDto;


            HttpEntity<List<Integer>> httpEntityIntegers;

            ResponseEntity<Doctor> responseEntityDoctor;
            ResponseEntity<Category[]> responseEntityCats;
            ResponseEntity<Hospital> responseEntityHos;
            ResponseEntity<Appointment[]> responseEntityAppo;

            List<Integer> integersCat = new ArrayList<>();

            Date dateNow = new Date();

            for (Channelling channelling : channellings) {

                if (Date.from(channelling.getEndTime().plusSeconds(60 * 60)).before(dateNow)) {
                    if (channelling.getStatus().equals("1")) {
                        responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/updateStatusByChannelling/" + channelling.getId() + "/" + 4, HttpMethod.PUT, httpEntityString, Boolean.class);

                        if (responseEntityBoolean.getBody()) {

                            channelling.setStatus("4");
                            channellingRepository.save(channelling);

                        }else{
                            throw new ChannellingException("Channelling searchByHospital(Appointment status update to 4) exception occurred in ChannellingServiceImpl.searchByHospital", null);
                        }

                    }
                }
                if (Date.from(channelling.getEndTime().plusSeconds(60 * 60 * 2)).before(dateNow)) {
                    if (channelling.getStatus().equals("1")) {
                        responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/updateStatusByChannelling/" + channelling.getId() + "/" + 4, HttpMethod.PUT, httpEntityString, Boolean.class);

                        if (responseEntityBoolean.getBody()) {

                            channelling.setStatus("4");
                            channellingRepository.save(channelling);

                        }else{
                            throw new ChannellingException("Channelling searchByHospital(Appointment status update to 4) exception occurred in ChannellingServiceImpl.searchByHospital", null);
                        }
                    } else if (channelling.getStatus().equals("2")) {

                        responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/updateStatusByChannelling/" + channelling.getId() + "/" + 3, HttpMethod.PUT, httpEntityString, Boolean.class);

                        if (responseEntityBoolean.getBody()) {

                        channelling.setStatus("3");
                        channellingRepository.save(channelling);

                        }else{
                            throw new ChannellingException("Channelling searchByHospital(Appointment status update to 3) exception occurred in ChannellingServiceImpl.searchByHospital", null);
                        }

                    }
                }

                channellingDto = new ChannellingDto();


                integersCat.removeAll(integersCat);

                httpHeaders = new HttpHeaders();
                httpEntityString = new HttpEntity<>("", httpHeaders);

                responseEntityDoctor = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_DOCTOR_SERVICE + "/doctor/find/" + channelling.getDoctor(), HttpMethod.GET, httpEntityString, Doctor.class);

                if (null != responseEntityDoctor.getBody()) {


                    channellingDto.setDoctor(responseEntityDoctor.getBody());
                    channellingDto.getDoctor().setContact("");

                    channellingDto.getDoctor().getDoctorCategories().forEach(doctorCategory -> integersCat.add(doctorCategory.getCategoryid()));

                    if (null == channellingSearchDTO.getCategory() || integersCat.contains(channellingSearchDTO.getCategory())) {

                        httpHeaders = new HttpHeaders();
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                        httpHeaders.add("Authorization", token);

                        httpEntityIntegers = new HttpEntity<>(integersCat, httpHeaders);

                        responseEntityCats = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CATGORY_SERVICE + "/category/searchByIds", HttpMethod.POST, httpEntityIntegers, Category[].class);

                        if (null != responseEntityCats.getBody()) {


                            channellingDto.setCategories(responseEntityCats.getBody());

                            httpHeaders = new HttpHeaders();
                            httpEntityString = new HttpEntity<>("", httpHeaders);


                            responseEntityHos = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findById/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Hospital.class);

                            if (null != responseEntityCats.getBody()) {

                                httpHeaders = new HttpHeaders();
                                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                httpHeaders.add("Authorization", token);

                                httpEntityString = new HttpEntity<>("", httpHeaders);


                                responseEntityAppo = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_APPOINTMENT_SERVICE + "/appointment/searchByChannellingIdAndStatus/" + channelling.getId(), HttpMethod.GET, httpEntityString, Appointment[].class);

                                if (null != responseEntityAppo.getBody()) {

                                    channellingDto.setAppointments(responseEntityAppo.getBody());
                                    channellingDto.setHospital(responseEntityHos.getBody());

                                    channellingDto.setId(channelling.getId());
                                    channellingDto.setEndTime(Date.from(channelling.getEndTime()));
                                    channellingDto.setPrice(channelling.getPrice());
                                    channellingDto.setRoom(channelling.getRoom());
                                    channellingDto.setStartTime(Date.from(channelling.getStartTime()));
                                    channellingDto.setStatus(channelling.getStatus());

                                    channellingDtos.add(channellingDto);

                                } else {
                                    throw new ChannellingException("Channelling find(Appointments) exception occurred in ChannellingServiceImpl.find", null);
                                }
                            } else {
                                throw new ChannellingException("Channelling find(Hospital) exception occurred in ChannellingServiceImpl.find", null);
                            }

                        } else {
                            throw new ChannellingException("Channelling find(Category) exception occurred in ChannellingServiceImpl.find", null);
                        }
                    }
                } else {
                    throw new ChannellingException("Channelling find(Doctor) exception occurred in ChannellingServiceImpl.find", null);
                }

            }

            Collections.reverse(channellingDtos);
            return channellingDtos;

        } else {
            return null;
        }
    }

    @Override
    public ResponseDto startChannelling(int id, String token, String name) {

        try {

            Optional<Channelling> optional = channellingRepository.findById(id);

            if (optional.isPresent()) {

                Channelling channelling = optional.get();

                if ("1".equals(channelling.getStatus())) {

                    HttpHeaders httpHeaders;

                    HttpEntity<String> httpEntityString;

                    httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.add("Authorization", token);

                    httpEntityString = new HttpEntity<>("", httpHeaders);

                    ResponseEntity<Boolean> responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CLIENT_SERVICE + "client/findByEmailAndHospital/" + name + "/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Boolean.class);

                    if (responseEntityBoolean.getBody()) {


                        channelling.setStatus("2");

                        channellingRepository.save(channelling);

                        System.out.println("Channelling Started Successfully.!");
                        return new ResponseDto(true, "Channelling Started Successfully.!");

                    } else {
                        System.out.println("Invalid Channelling to Start.!");
                        return new ResponseDto(false, "Invalid Channelling to Start.!");
                    }


                } else {
                    System.out.println("Invalid Channelling.!");
                    return new ResponseDto(false, "Invalid Channelling.!");
                }

            } else {
                System.out.println("Invalid Channelling.!");
                return new ResponseDto(false, "Invalid Channelling.!");
            }


        } catch (Exception e) {
            throw new ChannellingException("Channelling start exception occurred in ChannellingServiceImpl.start", e);
        }
    }

    @Override
    public ResponseDto finishChannelling(int id, String token, String name) {

        try {

            Optional<Channelling> optional = channellingRepository.findById(id);

            if (optional.isPresent()) {

                Channelling channelling = optional.get();

                if ("2".equals(channelling.getStatus())) {

                    HttpHeaders httpHeaders;

                    HttpEntity<String> httpEntityString;

                    httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.add("Authorization", token);

                    httpEntityString = new HttpEntity<>("", httpHeaders);

                    ResponseEntity<Boolean> responseEntityBoolean = restTemplate.exchange("http://" + ChannellingServiceApplication.DOMAIN_CLIENT_SERVICE + "client/findByEmailAndHospital/" + name + "/" + channelling.getHospital(), HttpMethod.GET, httpEntityString, Boolean.class);

                    if (responseEntityBoolean.getBody()) {


                        channelling.setStatus("3");

                        channellingRepository.save(channelling);

                        System.out.println("Channelling Finished Successfully.!");
                        return new ResponseDto(true, "Channelling Finished Successfully.!");

                    } else {
                        System.out.println("Invalid Channelling to Finish.!");
                        return new ResponseDto(false, "Invalid Channelling to Finish.!");
                    }


                } else {
                    System.out.println("Invalid Channelling.!");
                    return new ResponseDto(false, "Invalid Channelling.!");
                }

            } else {
                System.out.println("Invalid Channelling.!");
                return new ResponseDto(false, "Invalid Channelling.!");
            }


        } catch (Exception e) {
            throw new ChannellingException("Channelling finish exception occurred in ChannellingServiceImpl.finish", e);
        }
    }

    @Override
    public Channelling findById(Integer id) {
        Optional<Channelling> optional = channellingRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

}
