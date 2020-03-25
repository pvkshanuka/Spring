package lk.e_channelling.patient_service.servicers;

import lk.e_channelling.patient_service.ClientServiceApplication;
import lk.e_channelling.patient_service.dto.Login;
import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.exception.ClientException;
import lk.e_channelling.patient_service.models.Client;
import lk.e_channelling.patient_service.repository.ClientRepository;
import lk.e_channelling.patient_service.support.Validation;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

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
                client.setUser_id(1);
                client.setStatus("1");

                System.out.println("AWAAA");

                if (searchBeforeSave(client).isEmpty()) {

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                    HttpEntity<Object> httpEntity = new HttpEntity<>(new Login(client.getEmail(), client.getPassword(), 1), httpHeaders);

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
    public ResponseDto update(Client client) {

        try {

            Optional<Client> optional = clientRepository.findById(client.getId());


            if (optional.isPresent()) {

                Client clientDB = optional.get();

                if (clientDB.getStatus().equals("1")) {

//                client.setContact(clientDB.getContact());
                    client.setEmail(clientDB.getEmail());
                    client.setStatus("1");

                    if (validation.saveValidator(client)) {

                        clientRepository.save(client);

                        System.out.println("Client Updated Successfully.!");
                        return new ResponseDto(true, "Client Updated Successfully.!");
                    } else {
                        return new ResponseDto(false, "Invalid Client Details.!");
                    }
                } else {
                    return new ResponseDto(true, "Deleted Client.!");
                }
            } else {
                System.out.println("Invalid Client ID.!");
                return new ResponseDto(false, "Invalid Client ID.!");
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

                    System.out.println("Invalid Clinet ID.!");
                    return new ResponseDto(false, "Invalid Clinet ID.!");

                }

            } else {
                System.out.println("Invalid Clinet ID.!");
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
    public Boolean findById(Integer id) {
        return clientRepository.findById(id).isPresent();
    }

}
