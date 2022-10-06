package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;

import java.security.Principal;

@Controller
public class HelloController {

  private UserRepository userRepository;

    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String hello2(){
        return "hello";
    }

    @GetMapping("/hello")
    public String aboutUser(Model model, Principal principal){
            User user=userRepository.findByName(principal.getName()).get();
            model.addAttribute("user", user);
        return "hello";
    }




}
