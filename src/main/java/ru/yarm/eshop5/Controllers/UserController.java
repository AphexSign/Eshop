package ru.yarm.eshop5.Controllers;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.eshop5.Models.Role;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;
import ru.yarm.eshop5.Services.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users=userService.getAllByOrderByIdAsc();
        model.addAttribute("users", users);
        return "users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}/ban")
    public String ban(@PathVariable Long id) {
        userService.banUser(id);
        return "redirect:/users";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}/unban")
    public String unban(@PathVariable Long id) {
        userService.unbanUser(id);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}/mk_manager")
    public String mk_manager(@PathVariable Long id) {
        userService.mk_manager(id);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }


}
