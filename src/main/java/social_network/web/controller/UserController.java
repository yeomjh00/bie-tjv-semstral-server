package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import social_network.web.controller.asset.UserRegisterForm;
import social_network.web.domain.User;
import social_network.web.service.UserService;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    public static final String userRegister = "/users/new";
    public static final String allUsers = "/users/userList";
    public static final String welcomeNewUser = "/users/welcomeNewUser";

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping(userRegister)
    public String createForm(Model model){
        return userRegister;
    }

    @PostMapping(userRegister)
    public String createUser(UserRegisterForm form, Model model){
        User user = new User();
        user.setRealName(form.getRealName());
        user.setUsername(form.getUsername());
        user.setIntroduction(form.getIntroduction());

        if (null == form.getUserStatus()){
            user.setUserStatusTrial();
        } else{
            user.setUserStatusMembership();
        }
        userService.save(user);
        log.info("User saved successfully");
        model.addAttribute("user", user);
        return welcomeNewUser;
    }

    @GetMapping(allUsers)
    public String userList(Model model){
        var users = userService.findAll();
        model.addAttribute("users", users);
        return allUsers;
    }
}
