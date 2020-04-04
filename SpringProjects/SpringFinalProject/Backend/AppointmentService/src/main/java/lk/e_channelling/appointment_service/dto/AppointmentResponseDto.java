package lk.e_channelling.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {

    private Integer id;

    private ChannellingDto channellingDto;

    private Instant date;

    private String status;

}
