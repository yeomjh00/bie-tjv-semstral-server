package social_network.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import social_network.web.controller.asset.UserRegisterForm;
import social_network.web.domain.User;
import social_network.web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    public static final String userRegister = "users/new";
    public static final String allUsers = "users/userList";

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(userRegister)
    public String createForm(Model model){
        model.addAttribute("userRegisterForm", userRegister);
        return userRegister;
    }

    @PostMapping(userRegister)
    public String create(UserRegisterForm form){
        User user = new User();
        user.setRealName(form.getRealName());
        user.setUsername(form.getUsername());

        userService.save(user);

        return "redirect:/";
    }

    @GetMapping(allUsers)
    public String list(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("allUsers", users);
        return allUsers;
    }
}
