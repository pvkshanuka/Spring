package lk.kusal.spring_project_practise.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String email;

    public RegisterRequest() {
    }

    public RegisterRequest(String uname, String pword, String email) {
        this.username = uname;
        this.password = pword;
        this.email = email;
    }
}
