package social_network.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import social_network.web.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;

    private static final String homePage = "/";
    private static final String userPage = "/users/{id}";

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(homePage)
    public String home(){
        return "home";
    }

    @PostMapping(homePage)
    public String userPage(Model model, @PathVariable Long id){
        var user = userService.findById(id);
        model.addAttribute("user", user);
        return userPage;
    }
}
