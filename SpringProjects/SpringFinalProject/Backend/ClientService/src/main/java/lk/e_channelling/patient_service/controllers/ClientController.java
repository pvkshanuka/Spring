package lk.e_channelling.patient_service.controllers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Client;
import lk.e_channelling.patient_service.servicers.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {


    @Autowired
    ClientService patientService;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Client client) {

        try {

            return patientService.save(client);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Save Failed.!");
        }

    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Client client) {

        try {
                return patientService.update(client);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Update Error.!");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {

        try {

            return patientService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Client> search(@RequestBody Client client) {
        try {

            return patientService.search(client);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Client test() {



        Client client = new Client(1, "Kusal Shanuka", 24, "pvkshanuka@gmail.com", "0772101676", "1");

        return client;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/findBYId/{id}")
    public boolean findById(@PathVariable Integer id){

        System.out.println("findBYId");

        return patientService.findById(id);
    }

//    public boolean checkAppointments(Patient patient) {
//
//        if (null == patient.getAppointments() || patient.getAppointments().isEmpty()) {
//            return true;
//        } else {
//
//            boolean isFound = true;
//
//            List<Appointment> appointments = patient.getAppointments();
//            for (Appointment appointment : appointments) {
//                if (appointmentService.searchById(appointment.getId())) {
//                    isFound = false;
//                    break;
//                }
//            }
//
//            return isFound;
//
//        }

    }
