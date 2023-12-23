package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import social_network.web.controller.asset.UserRegisterForm;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.repository.UserRepository;
import social_network.web.service.PostService;
import social_network.web.service.UserResourceService;
import social_network.web.service.UserService;

import java.util.List;

@Slf4j
@Controller
public class UserPageController {
    private final UserService userService;

    private final UserResourceService userResourceService;
    private final PostService postService;

    public static final String userPage = "/users/{id}";

    public static final String userPageEdit = "/users/{id}/edit";

    public static final String userWithdrawal = "/users/{id}/withdrawal";

    public static final String userPosts = "/users/{id}/myposts";

    public static final String likedByUser = "/users/{id}/likedposts";

    // @TODO: move to media controller
    public static final String userMusicLists = "/users/{id}/musiclists";

    @Autowired
    public UserPageController(UserService userService,
                              PostService postService,
                              UserResourceService userResourceService){
        this.userService = userService;
        this.userResourceService = userResourceService;
        this.postService = postService;
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

        return "users/edit";
    }

    @PostMapping(userPageEdit)
    public String userPageEditPost(@PathVariable Long id, UserRegisterForm form){
        User user = userService.findByIdOrThrow(id);
        User editedUser = User.builder()
                .username(form.getUsername())
                .realName(user.getRealName())
                .introduction(form.getIntroduction())
                .build();

        if(userService.CheckValidityAndDuplicate(editedUser)){
            log.info("Valid for modifying user information!");

            user.setUsername(form.getUsername());
            if (null == form.getUserStatus()){
                user.setUserStatusTrial();
            } else{
                user.setUserStatusMembership();
            }
            userResourceService.updateUserInfoByUserId(id, user);

            return "redirect:/users/"+id;
        }
        log.info("Invalid for modifying user information!");
        return "redirect:/users/"+id+"/edit";
    }

    @GetMapping(userWithdrawal)
    public String userPageDelete(Model model, @PathVariable Long id){
        User user = userService.findByIdOrThrow(id);
        model.addAttribute("user", user);
        log.info("user id: {} entered withdrawal page", id);
        return "users/withdrawal";
    }

    @PostMapping(userWithdrawal)
    public String userPageDeletePost(@PathVariable Long id, @RequestParam String confirm){
        User user = userService.findByIdOrThrow(id);
        log.info("Confirmation Statement: {}", confirm);
        log.info("Actual Statement: {}", "withdraw "+ user.getUsername());
        if (confirm.equals("withdraw "+ user.getUsername())) {
            userResourceService.deleteUserInfoByUserId(id);
            return "redirect:/";
        }
        else
            return "redirect:/users/"+id+"/withdrawal";
    }

}
