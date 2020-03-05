package lk.kusal.spring_project_practise.service;

import lk.kusal.spring_project_practise.dto.PostDto;
import lk.kusal.spring_project_practise.exception.PostNotFoundException;
import lk.kusal.spring_project_practise.model.Post;
import lk.kusal.spring_project_practise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public void createPost(PostDto postDto) {

//        User user = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user loged in"));
//
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setUsername(user.getUsername());
//        post.setCreatedOn(Instant.now());
//        post.setUpdatedOn(Instant.now());
//
//        postRepository.save(post);

        Post post = mapFromDtotoPost(postDto);
        postRepository.save(post);

    }

    public List<PostDto> showAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());

    }


    private Post mapFromDtotoPost(PostDto postDto) {
        User user = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user loged in"));

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUsername(user.getUsername());
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        return post;
    }

    private PostDto mapFromPostToDto(Post post) {

        return new PostDto(post.getId(), post.getContent(), post.getTitle(), post.getUsername());

    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }
}
