package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import social_network.web.controller.asset.PostRegisterForm;
import social_network.web.domain.Post;
import social_network.web.service.PostService;
import social_network.web.service.UserResourceService;
import social_network.web.service.UserService;

@Slf4j
@Controller
public class PostViewController {

    private final PostService postService;
    private final UserService userService;

    public static final String allPosts = "/posts/postList";

    public static final String specificPost = "/posts/{postId}";

    public static final String allMyPosts = "/users/{id}/myposts";
    public static final String mySpecificPost = "/users/{id}/myposts/{postId}";
    public static final String likedPosts = "/users/{id}/likedposts";

    @Autowired
    public PostViewController(UserService userService, PostService postService){
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(allPosts)
    public String viewAllPosts(Model model){
        model.addAttribute("posts", postService.findAll());
        log.info("Viewing all posts");
        return allPosts;
    }

    @GetMapping(specificPost)
    public String viewSpecificPost(Model model, @PathVariable Long postId){
        Post post = postService.findByIdOrThrow(postId);
        model.addAttribute("post", post);
        log.info("Viewing specific post");
        return "posts/viewPost";
    }

    @GetMapping(mySpecificPost)
    public String viewMySpecificPost(Model model, @PathVariable Long id, @PathVariable Long postId){
        var post = postService.findByIdOrThrow(postId);
        var user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        log.info("Viewing specific post");
        return "posts/viewMyPost";
    }

    @GetMapping(allMyPosts)
    public String viewAllMyPosts(Model model, @PathVariable Long id){
        var user = userService.findByIdOrThrow(id);
        var myposts = postService.findAllByAuthorId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("posts", myposts);
        log.info("Viewing all my posts");
        return "posts/viewMyPosts";
    }

    @GetMapping(likedPosts)
    public String viewLikedPosts(Model model, @PathVariable Long id){
        var user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.findLikedPostsByUserId(id));
        log.info("Viewing all my posts");
        return "posts/viewMyPosts";
    }
}
