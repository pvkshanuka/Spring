package lk.e_channelling.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSearchDto {

    private Integer client;
    private Integer doctor;
    private Instant date;
    private String status;

}
