package lk.e_channelling.patient_service.controllers;

import lk.e_channelling.patient_service.ClientServiceApplication;
import lk.e_channelling.patient_service.dto.LoginRequestDto;
import lk.e_channelling.patient_service.dto.LoginResponseDto;
import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Client;
import lk.e_channelling.patient_service.servicers.ClientService;
import lk.e_channelling.patient_service.support.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/client")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {


    @Autowired
    ClientService clientService;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Client client) {
        System.out.println("SAVE AWA");
        try {

            client.setType(ClientServiceApplication.CLIENT_TYPE_CUSTOMER);
            return clientService.save(client);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Save Failed.!");
        }

    }
    //    Access By [Manager]
    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/saveManager")
    public ResponseDto saveManager(@RequestBody Client client, @RequestHeader("Authorization") String token, Principal principal) {
        System.out.println("SAVEMANAGER AWA");
        try {

            client.setType(ClientServiceApplication.CLIENT_TYPE_CUSTOMER);
            return clientService.saveManager(client,token,principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Save Failed.!");
        }

    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Client client, Principal principal) {

        try {
                return clientService.update(client, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Update Error.!");
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/password")
    public ResponseDto updatePw(@RequestBody Client client, @RequestHeader("Authorization") String token, Principal principal) {

        try {
            return clientService.updatePw(client, principal.getName(),token);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Update Error.!");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {

        try {

            return clientService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Client Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Client> search(@RequestBody Client client) {
        try {

            return clientService.search(client);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println("LOGIN");
        try {

            return clientService.login(loginRequestDto);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new LoginResponseDto(null,"","",null,"","",null,"Client Login Failed.!", false);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Client test() {


        return new Client(1, 1,null, "Kusal Shanuka", 24, "pvkshanuka@gmail.com","", "0772101676", "1",1);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/findBYId/{id}")
    public boolean findById(@PathVariable Integer id){
        return clientService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findDetailsById/{id}")
    public Client findDetailsById(@PathVariable Integer id, Principal principal){
        return clientService.findDetailsById(id, principal.getName());
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/findDetailsByIdClear/{id}")
    public Client findDetailsById(@PathVariable Integer id){
        return clientService.findDetailsById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByEmailAndHospital/{email}/{hospital}")
    public boolean findByEmailAndHospital(@PathVariable String email,@PathVariable Integer hospital){
        return clientService.findByEmailAndHospital(email,hospital);
    }

//    Access By [User]
    @RequestMapping(method = RequestMethod.GET, value = "/findUsernameById/{id}")
    public String findUsernameById(@PathVariable Integer id){
        return clientService.findUsernameById(id);
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

    @PreAuthorize("hasRole('ROLE_client')")
    @RequestMapping(method = RequestMethod.GET, value = "/test1")
    public void test1() {
            System.out.println("TEST 1 >>>>>>>>>>>>>>>>");
    }

    @PreAuthorize("hasRole('ROLE_operator')")
    @RequestMapping(method = RequestMethod.GET, value = "/test2")
    public void test2() {
        System.out.println("TEST 2 >>>>>>>>>>>>>>>>");
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.GET, value = "/test3")
    public void test3() {
        System.out.println("TEST 3 >>>>>>>>>>>>>>>>");
    }

    }
