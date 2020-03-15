package lk.e_channelling.doctor_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private boolean isSuccess;
    private String message;

}
