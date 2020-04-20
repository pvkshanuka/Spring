package lk.e_channeling.hospital_service.dto;

import lk.e_channeling.hospital_service.commonModels.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponseDto {

    private Integer id;

    private String name;

    private String city;

    private String email;

    private String contact;

    private Client[] clients;

    private String status;


}
