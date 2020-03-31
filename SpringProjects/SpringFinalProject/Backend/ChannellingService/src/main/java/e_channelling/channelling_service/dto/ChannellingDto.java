package e_channelling.channelling_service.dto;

import e_channelling.channelling_service.commonModels.Category;
import e_channelling.channelling_service.commonModels.Doctor;
import e_channelling.channelling_service.commonModels.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannellingDto {

    private Integer id;

    private Hospital hospital;

    private Doctor doctor;

    private Category[] categories;

    private String room;

    private Double price;

    private Date startTime;

    private Date endTime;

    private String status;

}
