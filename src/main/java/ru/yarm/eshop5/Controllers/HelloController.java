package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yarm.eshop5.Models.News;
import ru.yarm.eshop5.Models.Role;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Services.NewsService;
import ru.yarm.eshop5.Services.RegistrationService;
import ru.yarm.eshop5.Services.UserService;
import ru.yarm.eshop5.Services.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class HelloController {


  private final UserService userService;
  private final UserValidator userValidator;
  private final RegistrationService registrationService;
  private final NewsService newsService;

    public HelloController(UserService userService, UserValidator userValidator, RegistrationService registrationService, NewsService newsService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String hello2(Model model){
        List<News> newsList=newsService.getAllNewsSortByDescAndActive();
        model.addAttribute("newslist",newsList);

        return "hello";
    }


    @GetMapping("/hello")
    public String aboutUser(Model model, Principal principal){
            //User user=userRepository.findByName(principal.getName()).get();

        if(principal!=null) {
            User user = userService.getUserByName(principal.getName());
        }
        else {
            List<News> newsList=newsService.getAllNewsSortByDescAndActive();
            model.addAttribute("newslist",newsList);
            return "hello";

        }
        List<News> newsList=newsService.getAllNewsSortByDescAndActive();
        User user = userService.getUserByName(principal.getName());

            //Проверка на роль
            if(user.getRole()!=Role.BANNED){
                model.addAttribute("user", user);
                model.addAttribute("newslist",newsList);
                return "hello";
            } else {
                model.addAttribute("user", user);
                return "banned";}
    }


    @GetMapping("user/{id}/edit")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {

        User tmpUser = userService.getUserById(id);

        System.err.println(tmpUser.getId());
        model.addAttribute("user", tmpUser);
        return "user_edit";
    }

    @PatchMapping ("/user_edit")
    public String UpdateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            for(int i =0;i<bindingResult.getAllErrors().size();i++){
                System.err.println(bindingResult.getAllErrors().get(i));
            }
            return "user_edit";};

        registrationService.updateUser(user);
        return "redirect:/hello";
    }


    @GetMapping("user/{id}/editLogin")
    public String editUserLogin(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {

        User tmpUser = userService.getUserById(id);
        model.addAttribute("user", tmpUser);
        return "user_edit_login";
    }



    @PatchMapping ("/user_edit_login")
    public String UpdateUserLogin(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate_login(user,bindingResult);

        if(bindingResult.hasErrors()) {
//            for(int i =0;i<bindingResult.getAllErrors().size();i++){
//                System.err.println(bindingResult.getAllErrors().get(i));
//            }
            return "user_edit_login";};

        registrationService.updateUser_login(user);
        return "redirect:/logout";
    }



    @GetMapping("user/{id}/editEmail")
    public String editUserEmail(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {

        User tmpUser = userService.getUserById(id);

        model.addAttribute("user", tmpUser);
        return "user_edit_email";
    }


    @PatchMapping ("/user_edit_email")
    public String UpdateUserEmail(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate_email(user,bindingResult);

        if(bindingResult.hasErrors()) {
            for(int i =0;i<bindingResult.getAllErrors().size();i++){
                System.err.println(bindingResult.getAllErrors().get(i));
            }
            return "user_edit_email";};

        registrationService.updateUser_email(user);
        return "redirect:/hello";
    }


    @GetMapping("user/{id}/editPassword")
    public String editUserPassword(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {

        User tmpUser = userService.getUserById(id);

        model.addAttribute("user", tmpUser);
        return "user_edit_password";
    }

    @PatchMapping ("/user_edit_password")
    public String UpdateUserPassword(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate_password(user,bindingResult);
        userValidator.validate_new_password(user,bindingResult);

        if(bindingResult.hasErrors()) {
            for(int i =0;i<bindingResult.getAllErrors().size();i++){
                System.err.println(bindingResult.getAllErrors().get(i));
            }
            return "user_edit_password";};

        registrationService.updateUser_password(user);
        return "redirect:/logout";
    }




}
