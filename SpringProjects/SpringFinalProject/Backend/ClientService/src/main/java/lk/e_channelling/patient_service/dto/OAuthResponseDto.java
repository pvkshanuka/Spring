package lk.e_channelling.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthResponseDto {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String scope;

}
