package ru.yarm.eshop5.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Services.RegistrationService;
import ru.yarm.eshop5.Services.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")                //Точка входа будет начинаться с этой папки!
public class AuthController {

    private final RegistrationService registrationService;          //Спрятаны методы регистрации: Шифрование + сохранение в БД
    private final UserValidator userValidator;                      //Добавляем проверочные методы

    @Autowired
    public AuthController(RegistrationService registrationService, UserValidator userValidator) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }


    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult){
        userValidator.validate(user,bindingResult);

        if(bindingResult.hasErrors()) return "/auth/registration";

        registrationService.register(user);
        return "redirect:/auth/login";
    }

}

