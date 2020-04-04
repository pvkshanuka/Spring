package lk.e_channelling.appointment_service.commonModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCategory implements Serializable {

    private Integer id;

    private Integer categoryid;

    Doctor doctor;

    private String status;

}
