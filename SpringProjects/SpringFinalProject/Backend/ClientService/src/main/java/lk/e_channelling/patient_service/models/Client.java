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
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private String email;

    @NotNull
    private String contact;

    @NotNull
    private String password;

    @NotNull
    private String status;

}
