package lk.e_channelling.patient_service.controllers;

import lk.e_channelling.patient_service.dto.ResponseDto;
import lk.e_channelling.patient_service.models.Appointment;
import lk.e_channelling.patient_service.models.Patient;
import lk.e_channelling.patient_service.servicers.AppointmentService;
import lk.e_channelling.patient_service.servicers.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {


    @Autowired
    PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Patient patient) {

        try {

            patient.setAppointments(null);
            patient.setId(null);
            patient.setStatus("1");
            return patientService.save(patient);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Patient Save Failed.!");
        }

    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Patient patient) {

        try {

            if (checkAppointments(patient)) {
                patient.setStatus("1");
                return patientService.update(patient);
            } else {
                return new ResponseDto(false, "Invalid Appointments.!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Patient Update Error.!");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {

        try {

            return patientService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Patient Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Patient> search(@RequestBody Patient patient) {
        try {

            patient.setStatus("1");
            return patientService.search(patient);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Patient test() {

        Appointment appointment1 = new Appointment(1, new Patient(1,"A",12,"E","1212","1",null), 1, Instant.now(), "1");
        Appointment appointment2 = new Appointment(2, new Patient(1,"A",12,"E","1212","1",null), 2, Instant.now(), "1");

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment1);
        appointments.add(appointment2);

        Patient patient = new Patient(1, "Kusal Shanuka", 24, "pvkshanuka@gmail.com", "0772101676", "1", appointments);

        return patient;

    }

    public boolean checkAppointments(Patient patient) {

        if (null == patient.getAppointments() || patient.getAppointments().isEmpty()) {
            return true;
        } else {

            boolean isFound = true;

            List<Appointment> appointments = patient.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointmentService.searchById(appointment.getId())) {
                    isFound = false;
                    break;
                }
            }

            return isFound;

        }

    }


}
