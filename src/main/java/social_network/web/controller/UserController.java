package social_network.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import social_network.web.controller.form.UserRegisterForm;
import social_network.web.domain.User;
import social_network.web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String createForm(){
        return "users/CreateMemberForm";
    }

    @PostMapping("/users/new") //data를 form에 넣어서 전송할 때 mapping된다.
    public String create(UserRegisterForm form){
        User user = new User();
        user.setRealName(form.getRealName());
        user.setUsername(form.getUsername());

        userService.save(user);

        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model){
        List<User> members = userService.findAll();
        model.addAttribute("members", members);
        return "users/UserList";
    }
}
