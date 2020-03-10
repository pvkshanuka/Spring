package lk.e_channelling.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private boolean isSuccess;
    private String error;

}
