package lk.e_channelling.patient_service.servicers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Client;
import lk.e_channelling.patient_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ResponseDto save(Client client) {
//
//        for (Appointment appointment : patient.getAppointments()) {
//            appointment.setPatient_id(patient.getId());
//        }

        client.setId(null);
        client.setStatus("1");

        if (searchBeforeSave(client).isEmpty()) {

            Client save = clientRepository.save(client);

            if (save.getId().equals(null)) {
                System.out.println("Client Save Failed.!");
                return new ResponseDto(false, "Client Save Failed.!");
            } else {
                System.out.println("Client Saved Successfully.!");
                return new ResponseDto(true, "Client Saved Successfully.!");
            }

        }else{
            System.out.println("Client Already Added.!");
            return new ResponseDto(true, "Client Already Added.!");
        }

    }

    @Override
    public ResponseDto update(Client client) {

        Optional<Client> optional = clientRepository.findById(client.getId());

        if (optional.isPresent()) {
            if (optional.get().getStatus().equals("1")) {
                System.out.println(client);
                clientRepository.save(client);
                System.out.println("Client Updated Successfully.!");
                return new ResponseDto(true, "Client Updated Successfully.!");
            } else {
                return new ResponseDto(true, "Deleted Client.!");
            }
        } else {
            System.out.println("Client Update Failed.!");
            return new ResponseDto(false, "Invalid Client ID.!");
        }

    }

    @Override
    public ResponseDto delete(int id) {

        Optional<Client> optional = clientRepository.findById(id);

        if (optional.isPresent()) {
            Client client = optional.get();
            client.setStatus("0");
            clientRepository.save(client);
            System.out.println("Client Deleted Successfully.!");
            return new ResponseDto(true, "Client Deleted Successfully.!");
        } else {
            System.out.println("Client Delete Failed.!");
            return new ResponseDto(false, "Invalid Client ID.!");
        }

    }

    @Override
    public List<Client> search(Client client) {

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

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id","name","age","appointments")
                .withIgnoreNullValues();

        Example<Client> example = Example.of(client, exampleMatcher);
        return clientRepository.findAll(example);

    }

}
