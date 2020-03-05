package lk.kusal.spring_project_practise.dto;

import lombok.Data;

@Data
public class PostDto {

    private Long id;
    private String content;
    private String title;
    private String username;

    public PostDto() {
    }

    public PostDto(Long id, String content, String title, String username) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.username = username;
    }

}
