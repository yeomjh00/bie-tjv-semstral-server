package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(userRegister)
    public String createForm(Model model){
        model.addAttribute("userRegisterForm", userRegister);
        return userRegister;
    }

    @PostMapping("/users/new")
    public String create(UserRegisterForm form, Model model){
        User user = new User();
        user.setRealName(form.getRealName());
        user.setUsername(form.getUsername());

        try {
            userService.save(user);
        } catch (IllegalArgumentException e){
            log.error("Trial of duplicated username", e);
            model.addAttribute("errorStatement", e.getMessage());
            return "redirect:/error";
        }
        catch (Exception e) {
            log.error("Error occurred while saving user", e);
            model.addAttribute("errorStatement", e.getMessage());
            return "redirect:/error";
        }
        log.info("User saved successfully");

        return "redirect:/";
    }

    @GetMapping(allUsers)
    public String list(Model model){
        var users = userService.findAll();
        model.addAttribute("users", users);
        return allUsers;
    }
}
