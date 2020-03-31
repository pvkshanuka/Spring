package e_channelling.channelling_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannellingSearchDTO {

    private Integer category;
    private Integer hospital;
    private Integer doctor;
    private Instant date;

}
