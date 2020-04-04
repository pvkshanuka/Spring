package lk.e_channelling.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannellingSearchByIdsDto {

    List<Integer> ids;
    Integer doctor;
    Instant date;

}
