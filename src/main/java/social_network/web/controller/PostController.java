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

@Slf4j
@Controller
public class PostController {

    private final PostService postService;

    public static final String postRegister = "/posts/new";
    public static final String allPosts = "/posts/postList";
    public static final String specificPost = "/posts/{id}";
    public static final String postEdit = "/posts/edit";

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping(allPosts)
    public String viewAllPosts(Model model){
        log.info("Viewing all posts");
        model.addAttribute("posts", postService.findAll());
        return allPosts;
    }

    @GetMapping(postRegister)
    public String createPostForm(Model model){
        log.info("Way on creating new post");
        model.addAttribute("postRegister", postRegister);
        return postRegister;
    }

    @PostMapping(postRegister)
    public String createPost(Model model, PostRegisterForm post){
        log.info("Creating new post");
        model.addAttribute("post", post);
        return "redirect:/";
    }


    @GetMapping(specificPost)
    public String viewSpecificPost(Model model, @PathVariable Long id){
        log.info("Viewing specific post");
        var post = postService.findById(id);
        if (post.isEmpty()){
            log.error("Post not found");
            model.addAttribute("errorStatement", "Wrong Post ID");
            return "redirect:/error";
        }
        model.addAttribute("post", postService.findById(id));
        return specificPost;
    }
}
