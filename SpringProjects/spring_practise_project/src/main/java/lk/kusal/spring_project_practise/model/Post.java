package lk.kusal.spring_project_practise.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Lob
    private String content;

    private Instant createdOn;

    private Instant updatedOn;

    @NotBlank
    private String username;

    public Post() {
    }

    public Post(@NotBlank String title, String content, Instant createdOn, @NotBlank String username) {
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.username = username;
    }

    public Post(Long id, @NotBlank String title, String content, Instant createdOn, @NotBlank String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
