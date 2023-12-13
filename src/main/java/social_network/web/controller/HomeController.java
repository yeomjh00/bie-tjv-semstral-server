package social_network.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import social_network.web.service.UserService;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
