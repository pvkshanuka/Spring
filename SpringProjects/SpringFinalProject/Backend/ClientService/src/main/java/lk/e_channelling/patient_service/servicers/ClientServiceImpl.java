package lk.e_channelling.patient_service.servicers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lk.e_channelling.patient_service.ClientServiceApplication;
import lk.e_channelling.patient_service.dto.*;
import lk.e_channelling.patient_service.exception.ClientException;
import lk.e_channelling.patient_service.models.Client;
import lk.e_channelling.patient_service.repository.ClientRepository;
import lk.e_channelling.patient_service.support.PasswordGenerator;
import lk.e_channelling.patient_service.support.Validation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    @Autowired
    Validation validation;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseDto save(Client client) {
//
//        for (Appointment appointment : patient.getAppointments()) {
//            appointment.setPatient_id(patient.getId());
//        }
        try {
//            System.out.println(client.getPassword());
            if (validation.saveValidator(client)) {

                client.setId(null);
                //just to pass validation
                client.setUser_id(1);
                client.setStatus("1");

                System.out.println("AWAAA");

                if (searchBeforeSave(client).isEmpty()) {

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                    HttpEntity<Object> httpEntity = new HttpEntity<>(new Login(client.getEmail(), client.getPassword(), 3), httpHeaders);

                    ResponseEntity<Integer> responseEntity = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_OAUTH_SERVICE + "/authenticate", HttpMethod.POST, httpEntity, Integer.class);

                    if (null != responseEntity.getBody()) {

                        client.setUser_id(responseEntity.getBody());
                        Client save = clientRepository.save(client);

                        System.out.println("Client Saved Successfully.!");
                        return new ResponseDto(true, "Client Saved Successfully.!");

                    } else {
                        System.out.println("Client Saving Failed.!");
                        return new ResponseDto(false, "Client Saving Failed.!");
                    }
                } else {
                    System.out.println("Client Already Added.!");
                    return new ResponseDto(false, "Client Already Added.!");
                }


            } else {
                System.out.println("Invalid Client Details.!");
                return new ResponseDto(false, "Invalid Client Details.!");
            }

        } catch (Exception e) {
            throw new ClientException("Client saving exception occurred in ClientServiceImpl.save", e);
        }
    }

    @Override
    public ResponseDto saveManager(Client client, String token, String name) {
        try {

            Optional<Client> optional = clientRepository.findByEmail(name);

            if (optional.isPresent()) {
                Client logedClient = optional.get();

                if (null != logedClient.getHospital() && logedClient.getType() == 2) {


//            System.out.println(client.getPassword());
                    if (validation.saveValidator(client)) {

                        client.setId(null);
                        client.setStatus("1");

                        if (searchBeforeSave(client).isEmpty()) {

                            if (sendWelcomeEmail(client.getEmail())) {

                                PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                                        .useDigits(true)
                                        .useLower(true)
                                        .useUpper(true)
                                        .build();

                                client.setPassword(passwordGenerator.generate(8));
                                client.setHospital(logedClient.getHospital());

                                System.out.println(client.getPassword() + " >>>>>>>>>>>>>");

                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                httpHeaders.add("Authorization", token);

                                HttpEntity<Object> httpEntity = new HttpEntity<>(new Login(client.getEmail(), client.getPassword(), 2), httpHeaders);

                                ResponseEntity<Integer> responseEntity = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_OAUTH_SERVICE + "/authenticate/saveManager", HttpMethod.POST, httpEntity, Integer.class);

                                if (null != responseEntity.getBody()) {

                                    client.setUser_id(responseEntity.getBody());
                                    client.setType(2);
                                    Client savedClient = clientRepository.save(client);

                                    sendCredentialsEmail(logedClient.getName(), savedClient.getName(), savedClient.getEmail(), savedClient.getPassword());

                                    System.out.println("Manager Saved Successfully.!");


                                    return new ResponseDto(true, "Manager Saved Successfully.!");

                                } else {
                                    System.out.println("Manager Saving Failed.!");
                                    return new ResponseDto(false, "Manager Saving Failed.!");
                                }

                            } else {
                                System.out.println("Invalid Email Address.!");
                                return new ResponseDto(false, "Invalid Email Address.!");
                            }

                        } else {
                            System.out.println("Manager Already Added.!");
                            return new ResponseDto(false, "Manager Already Added.!");
                        }


                    } else {
                        System.out.println("Invalid Manager Details.!");
                        return new ResponseDto(false, "Invalid Manager Details.!");
                    }
                } else {
                    System.out.println("No Privilage.!");
                    return new ResponseDto(false, "No Privilage.!");
                }
            } else {
                System.out.println("No Privilage.!");
                return new ResponseDto(false, "No Privilage.!");
            }

        } catch (Exception e) {
            throw new ClientException("Client savingManager exception occurred in ClientServiceImpl.saveManager", e);
        }
    }

    @Override
    public ResponseDto saveManagerByAdmin(Client client, String token, String name) {
        try {
            System.out.println("saveManagerByAdmin");
            Optional<Client> optional = clientRepository.findByEmail(name);

            if (optional.isPresent()) {
                Client logedClient = optional.get();

                if (null != logedClient.getHospital() && logedClient.getType() == 1) {


//            System.out.println(client.getPassword());
                    if (validation.saveValidator(client)) {

                        client.setId(null);
                        client.setStatus("1");

                        if (searchBeforeSave(client).isEmpty()) {

                            if (sendWelcomeEmail(client.getEmail())) {

                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                httpHeaders.add("Authorization", token);

                                HttpEntity<String> httpEntityString = new HttpEntity<>("", httpHeaders);

                                ResponseEntity<Boolean> responseEntityBool = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findByIdAndStatus/" + client.getHospital(), HttpMethod.GET, httpEntityString, Boolean.class);

                                if (responseEntityBool.getBody()) {

                                    PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                                            .useDigits(true)
                                            .useLower(true)
                                            .useUpper(true)
                                            .build();

                                    client.setPassword(passwordGenerator.generate(8));
//                                client.setHospital(logedClient.getHospital());

                                    System.out.println(client.getPassword() + " >>>>>>>>>>>>>");


                                    HttpEntity<Object> httpEntity = new HttpEntity<>(new Login(client.getEmail(), client.getPassword(), 2), httpHeaders);

                                    ResponseEntity<Integer> responseEntity = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_OAUTH_SERVICE + "/authenticate/saveManager", HttpMethod.POST, httpEntity, Integer.class);

                                    if (null != responseEntity.getBody()) {

                                        client.setUser_id(responseEntity.getBody());
                                        client.setType(2);
                                        Client savedClient = clientRepository.save(client);

                                        sendCredentialsEmail(logedClient.getName(), savedClient.getName(), savedClient.getEmail(), savedClient.getPassword());

                                        System.out.println("Manager Saved Successfully.!");


                                        return new ResponseDto(true, "Manager Saved Successfully.!");

                                    } else {
                                        System.out.println("Manager Saving Failed.!");
                                        return new ResponseDto(false, "Manager Saving Failed.!");
                                    }

                                } else {
                                    System.out.println("Invalid Hospital.!");
                                    return new ResponseDto(false, "Invalid Hospital.!");
                                }

                            } else {
                                System.out.println("Invalid Email Address.!");
                                return new ResponseDto(false, "Invalid Email Address.!");
                            }

                        } else {
                            System.out.println("Manager Already Added.!");
                            return new ResponseDto(false, "Manager Already Added.!");
                        }


                    } else {
                        System.out.println("Invalid Manager Details.!");
                        return new ResponseDto(false, "Invalid Manager Details.!");
                    }
                } else {
                    System.out.println("No Privilage.!");
                    return new ResponseDto(false, "No Privilage.!");
                }
            } else {
                System.out.println("No Privilage.!");
                return new ResponseDto(false, "No Privilage.!");
            }

        } catch (Exception e) {
            throw new ClientException("Client savingManager exception occurred in ClientServiceImpl.saveManager", e);
        }
    }

    @Override
    public boolean sendWelcomeEmail(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Welcome");
            message.setText("Welcome to Medicare E-Channelling website.");
            javaMailSender.send(message);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean sendCredentialsEmail(String createdname, String name, String email, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Welcome " + name);
            message.setText("You are appointed as a hospital manager in Medicare E-Channelling Websile by " + createdname + "\n" +
                    "Your credentials below \n" +
                    "Username : " + email + "\n" +
                    "Password : " + password);
            javaMailSender.send(message);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendCredentialsUpdateEmail(String name, String email, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reseted by Admin ");
            message.setText("Hi " + name + ",\nYou are password is reseted by Admin, Please use new password to login.\n" +
                    "Your new password below \n" +
                    "Password : " + password);
            javaMailSender.send(message);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public ResponseDto update(Client client, String name) {


        try {

            client.setEmail(null);
            client.setPassword(null);
            client.setStatus(null);
            client.setType(null);

            if (validation.updateValidator(client)) {


                Optional<Client> optional = clientRepository.findById(client.getId());


                if (optional.isPresent()) {

                    Client clientDB = optional.get();

                    if (name.equals(clientDB.getEmail())) {

                        if (clientDB.getStatus().equals("1")) {

                            clientDB.setName(client.getName());
                            clientDB.setAge(client.getAge());
                            clientDB.setContact(client.getContact());


                            clientRepository.save(clientDB);

                            System.out.println("Client Updated Successfully.!");
                            return new ResponseDto(true, "Client Updated Successfully.!");
                        } else {
                            return new ResponseDto(true, "Deleted Client.!");
                        }

                    } else {
                        return new ResponseDto(false, "Invalid Client.!");
                    }

                } else {
                    System.out.println("Invalid Client ID.!");
                    return new ResponseDto(false, "Invalid Client ID.!");
                }


            } else {
                return new ResponseDto(false, "Invalid Client Details.!");
            }
        } catch (Exception e) {
            throw new ClientException("Client update exception occurred in ClientServiceImpl.update", e);
        }

    }

    @Override
    public ResponseDto delete(int id) {

        try {

            Optional<Client> optional = clientRepository.findById(id);

            if (optional.isPresent()) {
                Client client = optional.get();

                if ("1".equals(client.getStatus())) {

                    client.setStatus("0");
                    clientRepository.save(client);
                    System.out.println("Client Deleted Successfully.!");
                    return new ResponseDto(true, "Client Deleted Successfully.!");

                } else {

                    System.out.println("Invalid Client ID.!");
                    return new ResponseDto(false, "Invalid Client ID.!");

                }

            } else {
                System.out.println("Invalid Client ID.!");
                return new ResponseDto(false, "Invalid Client ID.!");
            }

        } catch (Exception e) {
            throw new ClientException("Client delete exception occurred in ClientServiceImpl.delete", e);
        }

    }

    @Override
    public List<Client> search(Client client) {

        client.setStatus("1");

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<Client> example = Example.of(client, exampleMatcher);
        return clientRepository.findAll(example);

    }

    @Override
    public List<Client> searchBeforeSave(Client client) {

//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
//                .withIgnorePaths("id", "name", "age", "appointments")
//                .withIgnoreNullValues();
//
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("id", "name", "age", "appointments")
                .withIgnoreNullValues();

        Example<Client> example = Example.of(client, exampleMatcher);
        return clientRepository.findAll(example);

    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        try {

            String credentials = ClientServiceApplication.OAUTH_CLIENT_ID + ":" + ClientServiceApplication.OAUTH_CLIENT_SECRET;

            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
            System.out.println(credentials);
            System.out.println(encodedCredentials);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            httpHeaders.add("Authorization", "Basic " + encodedCredentials);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "password");
            params.add("username", loginRequestDto.getUsername());
            params.add("password", loginRequestDto.getPassword());

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

            ResponseEntity<OAuthResponseDto> responseEntity = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_OAUTH_SERVICE + "/oauth/token?grant_type=" + ClientServiceApplication.OAUTH_GRANT_TYPE, HttpMethod.POST, httpEntity, OAuthResponseDto.class);

            HttpStatus statusCode = responseEntity.getStatusCode();

            System.out.println(responseEntity.getBody());

            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {

                OAuthResponseDto oAuthResponseDto = responseEntity.getBody();

                Optional<Client> optional = clientRepository.findByEmail(loginRequestDto.getUsername());


                if (optional.isPresent()) {
                    final Client client = optional.get();
                    if (client.getStatus().equals("1")) {


                        HttpEntity<String> httpEntityString = new HttpEntity<>("", httpHeaders);

                        ResponseEntity<Boolean> responseEntityBool = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_HOSPITAL_SERVICE + "/hospital/findByIdAndStatus/" + client.getHospital(), HttpMethod.GET, httpEntityString, Boolean.class);

                        if (responseEntityBool.getBody()) {

                            return new LoginResponseDto(client.getId(), client.getName(), client.getEmail(), client.getHospital(), oAuthResponseDto.getAccess_token(), oAuthResponseDto.getRefresh_token(), client.getType(), "", true);
                        } else {
                            return new LoginResponseDto(null, "", "", null, "", "", null, "Deleted Hospital.!", false);
                        }
                    } else {
                        return new LoginResponseDto(null, "", "", null, "", "", null, "Invalid Login Details.!", false);
                    }
                } else {
                    return new LoginResponseDto(null, "", "", null, "", "", null, "Invalid Login Details.!", false);
                }
            } else {
                return new LoginResponseDto(null, "", "", null, "", "", null, "Invalid Login Details.!", false);
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new LoginResponseDto(null, "", "", null, "", "", null, "Invalid Login Details.!", false);
            } else {
                throw new ClientException("Client login exception occurred in ClientServiceImpl.login", e);
            }
        }
    }

    @Override
    public Boolean findById(Integer id) {
        try {
            return clientRepository.findById(id).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String findUsernameById(Integer id) {
        Optional<Client> optional = clientRepository.findById(id);
        if (optional.isPresent()) {
            System.out.println(optional);
            return optional.get().getEmail();
        } else {
            return null;
        }
    }

    @Override
    public Client findDetailsById(Integer id, String name) {
        Optional<Client> optional = clientRepository.findById(id);
        if (optional.isPresent()) {
            Client client = optional.get();
            if (name.equals(client.getEmail())) {
                return client;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public Client findDetailsById(Integer id) {
        Optional<Client> optional = clientRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Client findDetailsByUsername(String username) {
        Optional<Client> optional = clientRepository.findByEmail(username);

        if (optional.isPresent()) {
            Client client = optional.get();
            return client;
        } else {
            return null;
        }

    }

    @Override
    public ResponseDto updatePw(Client client, String name, String token) {

        System.out.println(client + " >>>>>>>>>");
        try {

            if (validation.updatePwValidator(client)) {


                Optional<Client> optional = clientRepository.findById(client.getId());


                if (optional.isPresent()) {

                    Client clientDB = optional.get();

                    if (name.equals(clientDB.getEmail())) {

                        if (clientDB.getStatus().equals("1")) {


                            String credentials = ClientServiceApplication.OAUTH_CLIENT_ID + ":" + ClientServiceApplication.OAUTH_CLIENT_SECRET;

                            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
                            System.out.println(credentials);
                            System.out.println(encodedCredentials);

                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//                            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);
                            httpHeaders.add("Authorization", token);

                            HttpEntity<Object> httpEntity = new HttpEntity<>(new Login(clientDB.getEmail(), client.getPassword(), null), httpHeaders);

                            ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_OAUTH_SERVICE + "/authenticate", HttpMethod.PUT, httpEntity, Boolean.class);

                            if (responseEntity.getBody()) {

                                System.out.println("Client Updated Successfully.!");
                                return new ResponseDto(true, "Client Updated Successfully.!");

                            } else {
                                System.out.println("Client Update Failed.!");
                                return new ResponseDto(false, "Client Update Failed.!");
                            }

                        } else {
                            return new ResponseDto(true, "Deleted Client.!");
                        }

                    } else {
                        return new ResponseDto(false, "Invalid Client.!");
                    }

                } else {
                    System.out.println("Invalid Client ID.!");
                    return new ResponseDto(false, "Invalid Client ID.!");
                }


            } else {
                return new ResponseDto(false, "Invalid Client Details.!");
            }
        } catch (Exception e) {
            throw new ClientException("Client update exception occurred in ClientServiceImpl.update", e);
        }

    }

    @Override
    public ResponseDto resetPassword(Integer id, String name, String token) {

        System.out.println(id + " >>>>>>>>>");
        try {


            Optional<Client> optional = clientRepository.findById(id);


            if (optional.isPresent()) {

                Client clientDB = optional.get();

                if (clientDB.getType() == 2) {

                    if (clientDB.getStatus().equals("1")) {

                        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                                .useDigits(true)
                                .useLower(true)
                                .useUpper(true)
                                .build();

                        clientDB.setPassword(passwordGenerator.generate(8));

                        String credentials = ClientServiceApplication.OAUTH_CLIENT_ID + ":" + ClientServiceApplication.OAUTH_CLIENT_SECRET;

                        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
                        System.out.println(credentials);
                        System.out.println(encodedCredentials);

                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//                            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);
                        httpHeaders.add("Authorization", token);

                        HttpEntity<Object> httpEntity = new HttpEntity<>(new Login(clientDB.getEmail(), clientDB.getPassword(), null), httpHeaders);

                        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://" + ClientServiceApplication.DOMAIN_OAUTH_SERVICE + "/authenticate", HttpMethod.PUT, httpEntity, Boolean.class);

                        if (responseEntity.getBody()) {
                            sendCredentialsUpdateEmail(clientDB.getName(), clientDB.getEmail(), clientDB.getPassword());
                            System.out.println("Client Updated Successfully.!");
                            return new ResponseDto(true, "Client Updated Successfully.!");

                        } else {
                            System.out.println("Client Update Failed.!");
                            return new ResponseDto(false, "Client Update Failed.!");
                        }

                    } else {
                        return new ResponseDto(true, "Deleted Client.!");
                    }

                } else {
                    return new ResponseDto(false, "Invalid Client.!");
                }
            } else {
                return new ResponseDto(false, "Invalid Client.!");
            }

        } catch (Exception e) {
            throw new ClientException("Client update exception occurred in ClientServiceImpl.update", e);
        }

    }

    @Override
    public boolean findByEmailAndHospital(String email, Integer hospital) {
        try {
            return clientRepository.findByEmailAndHospital(email, hospital).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Client> findByHospitalAndStatusNot(Integer id) {
        try {
            return clientRepository.findByHospitalAndStatusNot(id, "0");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
