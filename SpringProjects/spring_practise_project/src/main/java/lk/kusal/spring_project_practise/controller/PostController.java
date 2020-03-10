package lk.kusal.spring_project_practise.controller;

import lk.kusal.spring_project_practise.dto.PostDto;
import lk.kusal.spring_project_practise.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> \n"+postDto.getTitle()+" - "+postDto.getContent());
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts(){
        return new ResponseEntity<>(postService.showAllPosts(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id){
        return new ResponseEntity<>(postService.readSinglePost(id),HttpStatus.OK);
    }

}
