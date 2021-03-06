package lk.e_channelling.patient_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer user_id;

    private Integer hospital;

    @NotNull
    private String name;

    private Integer age;

    @NotNull
    private String email;

    @Transient
    private String password;

    private String contact;

    @NotNull
    private String status;

    @NotNull
    private Integer type;

}
