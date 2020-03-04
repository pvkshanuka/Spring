package lk.kusal.spring_project_practise.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    public User() {
    }

    public User(String userName, String password, String email) {
        this.username = userName;
        this.password = password;
        this.email = email;
    }
}
