package lk.e_channelling.appointment_service.commonModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Client {

    private Integer id;

    private Integer user_id;

    private Integer hospital_id;

    private String name;

    private Integer age;

    private String email;

    private String contact;

    private String status;

    @NotNull
    private Integer type;

}
