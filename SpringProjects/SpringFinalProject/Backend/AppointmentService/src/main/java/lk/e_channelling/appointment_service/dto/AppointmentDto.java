package lk.e_channelling.appointment_service.dto;

import lk.e_channelling.appointment_service.commonModels.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Integer id;

    private Client client;

    private Integer channelling;

    private Instant date;

    private String status;

}
