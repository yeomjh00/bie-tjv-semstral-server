package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.PostDto;
import social_network.web.domain.Music;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.service.PictureService;
import social_network.web.service.PostService;
import social_network.web.service.UserResourceService;
import social_network.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class PostRestController {

    private final UserService userService;
    private final PostService postService;
    private final UserResourceService userResourceService;

    private final PictureService pictureService;


    @Autowired
    public PostRestController(UserService userService,
                              PostService postService,
                              UserResourceService userResourceService,
                              PictureService pictureService){
        this.userService = userService;
        this.postService = postService;
        this.userResourceService = userResourceService;
        this.pictureService = pictureService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> readAllPosts(){
        log.info("read all posts");
        List<PostDto> posts = postService.findAll().stream()
                .map(PostDto::Post2Dto)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public HttpStatus createPost(@RequestBody PostDto postDto){
        log.info("create post with: {}, {}, {}", postDto.getUserId(), postDto.getPictureDtos(), postDto.getMusicDto());

        Optional<User> user = userService.findById(postDto.getUserId());
        if (user.isEmpty()){
            log.info("user not found");
            return HttpStatus.NOT_FOUND;
        }
        if(!postDto.getPictureDtos().isEmpty() && postDto.getMusicDto() != null){
            postService.saveAllPicturesFromDto(postDto.getPictureDtos());
        }
        Post response = postService.save(Post.Dto2Post(postDto, user.get()));
        return response.getId() == -1 ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
    }

    @DeleteMapping("/posts")
    public void deleteAllPosts(){
        log.info("delete all posts");
        postService.deleteAll();
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> readPostById(@PathVariable Long post_id){
        log.info("read post by id: {}", post_id);
        PostDto result = PostDto.Post2Dto(postService.findById(post_id).orElse(null));
        HttpStatus status = result == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(status).body(result);
    }

    @PutMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long post_id, @RequestBody PostDto postDto){
        log.info("try to update post: {}", postDto);
        Optional<Post> post = postService.findById(post_id);
        if (post.isEmpty()){
            log.info("post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Post p = post.get();
        Music music = postDto.getMusicDto() == null ? null : Music.Dto2Music(postDto.getMusicDto());
        return postService.editPostContentAndTitle(post_id, postDto, music);
    }

    @DeleteMapping("/posts/{post_id}")
    public void deletePost(@PathVariable Long post_id){
        Optional<Post> post = postService.findById(post_id);
        if (post.isEmpty()){
            log.info("post not found");
            return;
        }
        log.info("delete post by id: {}", post_id);
        postService.deleteById(post_id);
    }

    @GetMapping("/posts/owned")
    public ResponseEntity<List<PostDto>> readSomeonePosts(@RequestParam Long user_id){
        log.info("read all posts");
        List<PostDto> posts = postService.findAllByAuthorId(user_id);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/liked")
    public ResponseEntity<List<PostDto>> readLikedPostsByUserId(@RequestParam Long user_id){
        log.info("read all posts");
        List<PostDto> posts = postService.findLikedPostsByUserId(user_id);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts/{post_id}/like")
    public void likePost(@RequestParam Long user_id, @PathVariable Long post_id){
        log.info("try: like post {} by user {}", post_id, user_id);
        Optional<User> user = userService.findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return;
        }
        userResourceService.likePost(user_id, post_id);
    }

    @DeleteMapping("/posts/{post_id}/like")
    public void unlikePost(@RequestParam Long user_id, @PathVariable Long post_id){
        log.info("try: unlike post {} by user {}", post_id, user_id);
        Optional<User> user = userService.findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return;
        }
        userResourceService.unlikePost(user_id, post_id);
    }
}
