package ru.yarm.eshop5.Controllers;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;
import ru.yarm.eshop5.Services.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
       // List<User> users=userRepository.findAllByOrderByIdAsc();
        List<User> users=userService.getAllByOrderByIdAsc();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{id}/ban")
    public String ban(@PathVariable Long id) {
        userService.banUser(id);
        return "redirect:/users";
    }
    @GetMapping("/users/{id}/unban")
    public String unban(@PathVariable Long id) {
        userService.unbanUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/mk_manager")
    public String mk_manager(@PathVariable Long id) {
        userService.mk_manager(id);
        return "redirect:/users";
    }


}
