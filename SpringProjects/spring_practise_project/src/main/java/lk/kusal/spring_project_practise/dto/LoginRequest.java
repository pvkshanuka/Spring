package lk.kusal.spring_project_practise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {
    }
}
