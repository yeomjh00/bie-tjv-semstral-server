package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import social_network.web.controller.asset.PostRegisterForm;
import social_network.web.domain.Music;
import social_network.web.domain.Picture;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.service.PostService;
import social_network.web.service.UserResourceService;
import social_network.web.service.UserService;

import java.util.ArrayList;

@Slf4j
@Controller
public class PostFuncController {
    private final PostService postService;
    private final UserService userService;
    private final UserResourceService userResourceService;

    public static final String newPost = "/users/{id}/newpost";
    public static final String editSpecificPost = "/users/{id}/myposts/{postId}/edit";
    public static final String likePost = "/posts/{postId}/like";


    @Autowired
    public PostFuncController(PostService postService, UserService userService, UserResourceService userResourceService){
        this.postService = postService;
        this.userService = userService;
        this.userResourceService = userResourceService;
    }

    @GetMapping(newPost)
    public String newPost(Model model, @PathVariable Long id){
        log.info("Creating new post");
        User user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);
        return "posts/newpost";
    }

    //@TODO: Add Media
    @PostMapping(newPost)
    public String createPost(Model model, @PathVariable Long id, PostRegisterForm form){
        User user = userService.findByIdOrThrow(id);
        Post post = Post.builder()
                        .title(form.getTitle())
                        .content(form.getContent())
                        .author(user)
                        .likes(new ArrayList<User>())
                        .pictures(new ArrayList <Picture>())
                         .songs(new ArrayList<Music>())
                        .build();
        postService.verifiedSave(post);
        log.info("Creating new post");
        return "redirect:/users/" + id;
    }

    @GetMapping(editSpecificPost)
    public String editSpecificPost(Model model, @PathVariable Long id, @PathVariable Long postId){
        log.info("Editing specific post");
        Post post = postService.findByIdOrThrow(postId);
        User user = userService.findByIdOrThrow(id);
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        return "posts/editpost";
    }

    @PostMapping(editSpecificPost)
    public String editSpecificPostPost(Model model, @PathVariable Long id, @PathVariable Long postId, PostRegisterForm form){
        Post post = postService.findByIdOrThrow(postId);
        Post updatedPost = postService.updatePostFromDto(form);
        log.info("Editing specific post");
        return "redirect:/users/" + id + "/myposts/";
    }
}
