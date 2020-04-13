package lk.e_channelling.appointment_service.controllers;

import lk.e_channelling.appointment_service.dto.AppointmentDto;
import lk.e_channelling.appointment_service.dto.AppointmentResponseDto;
import lk.e_channelling.appointment_service.dto.AppointmentSearchDto;
import lk.e_channelling.appointment_service.dto.ResponseDto;
import lk.e_channelling.appointment_service.models.Appointment;
import lk.e_channelling.appointment_service.servicers.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_client')")
    public ResponseDto save(@RequestBody Appointment appointment, @RequestHeader("Authorization") String token) {

        try {
            return appointmentService.save(appointment, token);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Appointment Adding Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {

        try {

            return appointmentService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Appointment Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Appointment> search(@RequestBody Appointment appointment) {
        try {

            return appointmentService.search(appointment);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Appointment test() {

        Appointment appointment = new Appointment(1, 1, 1, Instant.now(), "1");

        return appointment;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchByChannellingId/{id}")
    public Boolean searchByChannellingId(@PathVariable Integer id) {
        System.out.println("Channelling ID : "+id);
        return appointmentService.searchByChannellingId(id);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchByChannellingIdAndStatus/{id}")
    public List<AppointmentDto> searchByChannellingIdAndStatusNot(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        System.out.println("Channelling ID : "+id);

        return appointmentService.searchByChannellingIdAndStatusNot(id,token);

    }

//    Accessing [Client]
    @RequestMapping(method = RequestMethod.POST, value = "/getAppointments")
    public List<AppointmentResponseDto> getAppointments(@RequestBody AppointmentSearchDto appointmentSearchDto, @RequestHeader("Authorization") String token, Principal principal) {
        try {
            System.out.println("getAppointments "+principal.getName());
            System.out.println(appointmentSearchDto);
            return appointmentService.getAppointments(appointmentSearchDto, token,principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }

    }


}
