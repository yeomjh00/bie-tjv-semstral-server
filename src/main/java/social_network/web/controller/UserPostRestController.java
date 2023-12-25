package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.PostDto;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.service.PostService;
import social_network.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserPostRestController {

    private final UserService userService;
    private final PostService postService;

    public UserPostRestController(UserService userService,
                                  PostService postService){
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/posts/{user_id")
    public ResponseEntity<List<PostDto>> readAllPostsByUserId(@PathVariable Long user_id){
        log.info("read all posts by user id: {}", user_id);
        Optional<User> user = userService.findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.findMyPostsByUserId(user_id).stream()
                        .map(PostDto::Post2Dto)
                        .toList()
        );
    }

    @PostMapping("/posts/{user_id}")
    public ResponseEntity<PostDto> createPost(PostDto postDto, @PathVariable Long user_id){
        Optional<User> user = userService.findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info("create post: {}", postDto);
        Post p = Post.Dto2Post(postDto, user.get());
        postService.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(PostDto.Post2Dto(p));
    }

    @PutMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long post_id, PostDto postDto){
        log.info("update post: {}", postDto);
        Optional<Post> post = postService.findById(post_id);
        if (post.isEmpty()){
            log.info("post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Post p = post.get();
        p.setContent(postDto.getContent());
        p.setTitle(postDto.getTitle());

        return ResponseEntity.status(HttpStatus.OK).body(PostDto.Post2Dto(p));
    }

    @DeleteMapping("/posts/{post_id}")
    public void deletePost(@PathVariable Long post_id){
        log.info("delete post by id: {}", post_id);
        postService.deleteById(post_id);
    }

    @GetMapping("/users/{user_id}/liked")
    public  ResponseEntity<List<PostDto>> readAllLikedPostsByUserId(@PathVariable Long user_id){
        log.info("read all liked posts by user id: {}", user_id);
        Optional<User> user = userService.findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.findLikedPostsByUserId(user_id).stream()
                        .map(PostDto::Post2Dto)
                        .toList()
        );
    }
}
