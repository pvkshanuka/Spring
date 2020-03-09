package lk.e_channelling.patient_service.controllers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Appointment;
import lk.e_channelling.patient_service.models.Patient;
import lk.e_channelling.patient_service.servicers.AppointmentService;
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

            appointment.setId(null);
            appointment.setStatus("1");

            List<Appointment> search = search(appointment);

            if (search.isEmpty()) {
                return appointmentService.save(appointment);
            }else{
                return new ResponseDto(false, "Appointment Already Added.!");
            }
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

            appointment.setStatus("1");
            return appointmentService.search(appointment);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Appointment test() {

        Appointment appointment = new Appointment(1,new Patient(1,"A",12,"E","1212","1",null),1, Instant.now(),"1");

        return appointment;

    }


}
