package e_channelling.channelling_service.commonModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Id
    private Integer categoryid;

//    @Id
//    @JoinColumn
    @ManyToOne
    @JsonIgnore
    Doctor doctor;

    private String status;

}
