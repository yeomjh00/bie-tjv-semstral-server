package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.exception_handler.exception.InvalidAccessException;
import social_network.web.service.UserService;

import java.util.List;

@Slf4j
@Controller
public class UserPageController {
    private final UserService userService;

    public static final String userPage = "/users/{id}";

    public static final String userPageEdit = "/users/{id}/edit";

    public static final String userWithdrawal = "/users/{id}/withdrawal";

    public static final String userPosts = "/users/{id}/myposts";

    public static final String likedByUser = "/users/{id}/likedposts";

    // @TODO: move to media controller
    public static final String userMusicLists = "/users/{id}/musiclists";

    @Autowired
    public UserPageController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(userPage)
    public String userPage(Model model, @PathVariable Long id){
        User user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);

        return "users/personalPage";
    }

    @GetMapping(userPageEdit)
    public String userPageEdit(Model model, @PathVariable Long id){
        User user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);

        return "users/personalPageEdit";
    }

    @GetMapping(userWithdrawal)
    public String userPageDelete(Model model, @PathVariable Long id){
        User user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);
        return "redirect:/users/withdrawal";
    }

    @PostMapping(userWithdrawal)
    public String userPageDeletePost(@PathVariable Long id, @RequestParam String confirm){
        User user = userService.findByIdOrThrow(id);
        if (confirm.equals("withdraw "+ user.getUsername())) {
            // TODO: delete all the information about user.
            userService.deleteById(id);
            return "redirect:/";
        }
        else
            return "redirect:/users/"+id+"/withdrawal";
    }


    // TODO: userPosts and likedByUser should return different data
    // But It eventually rendered with same structure.
    @GetMapping("/users/{id}/myposts")
    public String userPosts(Model model, @PathVariable Long id){
        User user = userService.findByIdOrThrow(id);
        List<Post> posts = userService.findMyPostsByUserId(id);
        log.info("#posts of {}: {}", id, posts.size());
        model.addAttribute("posts", posts);
        return "users/" + id + "/myposts";
    }

    @GetMapping(likedByUser)
    public String likedByUser(Model model, @PathVariable Long id){
        User user = userService.findByIdOrThrow(id);
        List<Post> posts = userService.findLikedPostsByUserId(id);
        log.info("#liked posts of {}: {}", id, posts.size());
        model.addAttribute("posts", posts);
        return "users/" + id + "/likedposts";
    }

    //@GetMapping(userMusicLists)
}
