package lk.e_channelling.appointment_service.dto;

import lk.e_channelling.appointment_service.commonModels.Category;
import lk.e_channelling.appointment_service.commonModels.Doctor;
import lk.e_channelling.appointment_service.commonModels.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
