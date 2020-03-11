package lk.e_channelling.doctor_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String contact;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<DoctorCategory> doctorCategories;

    private String status;

    public Doctor(Integer id, @NotNull String name, @NotNull String contact, String status) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.status = status;
    }
}
