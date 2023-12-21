package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import social_network.web.domain.User;
import social_network.web.exception_handler.exception.InvalidAccessException;
import social_network.web.service.UserService;

import java.util.Optional;

@Slf4j
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
    public String userPage(@RequestParam("postedId") Long postedId){
        log.info("given id: {}", postedId);
        User user = userService.findById(postedId)
                        .orElseThrow(() -> new InvalidAccessException("User not Found"));
        return "redirect:users/"+user.getId();
    }
}
