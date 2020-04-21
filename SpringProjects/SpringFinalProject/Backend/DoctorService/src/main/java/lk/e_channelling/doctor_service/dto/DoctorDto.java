package lk.e_channelling.doctor_service.dto;

import lk.e_channelling.doctor_service.commonModels.Category;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private Integer id;

    private String name;

    private String contact;

    Category[] categories;

    private String status;

}
