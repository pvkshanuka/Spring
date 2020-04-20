package lk.e_channeling.hospital_service.commonModels;

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

    private Integer hospital;

    private String name;

    private Integer age;

    private String email;

    private String contact;

    private String status;

    private Integer type;

}
