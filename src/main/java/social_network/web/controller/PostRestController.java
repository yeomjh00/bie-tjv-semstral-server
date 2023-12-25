package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import social_network.web.controller.asset.PostDto;
import social_network.web.service.PostService;

import java.util.List;

@Slf4j
@RestController
public class PostRestController {

    private final PostService postService;

    @Autowired
    public PostRestController(PostService postService){
        this.postService = postService;
    }


    @GetMapping("/posts")
    public List<PostDto> readAllPosts(){
        log.info("read all posts");
        return postService.findAll().stream()
                .map(PostDto::Post2Dto)
                .toList();
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> readPostById(Long post_id){
        log.info("read post by id: {}", post_id);
        // Post2Dto handles null
        PostDto result = PostDto.Post2Dto(postService.findById(post_id).orElse(null));
        HttpStatus status = result.getId() == -1 ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(status).body(result);
    }
}

