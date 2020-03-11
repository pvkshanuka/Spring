package lk.e_channelling.appointment_service.controllers;

import lk.e_channelling.appointment_service.dto.ResponseDto;
import lk.e_channelling.appointment_service.models.Appointment;
import lk.e_channelling.appointment_service.servicers.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Appointment appointment) {

        try {
            return appointmentService.save(appointment);
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


}
