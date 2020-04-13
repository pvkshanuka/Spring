package lk.e_channelling.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private Integer id;
    private String name;
    private String username;
    private Integer hospital;
    private String token;
    private String refreshToken;
    private Integer type;
    private String message;
    private Boolean isSuccess;
}
