package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;

@Component
public class UserValidator implements Validator {

    private final SecurityUserDetailsService securityUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @Autowired
    public UserValidator(SecurityUserDetailsService securityUserDetailsService, PasswordEncoder passwordEncoder, UserService userService) {
        this.securityUserDetailsService= securityUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    //Проверка на отсутствие двойников в БД
    @Override
    public void validate(Object o, Errors errors) {
        User user=(User) o;

        try{
            securityUserDetailsService.loadUserByEmail(user.getEmail());
        }
        catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("email","","Пользователь с таким email уже есть");


        try {
            securityUserDetailsService.loadUserByUsername(user.getName());

        }
        catch (UsernameNotFoundException ignored){
            return; // ok
        }
        errors.rejectValue("name","","Пользователь с таким логином уже есть");
    }

    public void validate_login(Object o, Errors errors){
        User user=(User) o;
        try {
            securityUserDetailsService.loadUserByUsername(user.getName());

        }
        catch (UsernameNotFoundException ignored){
            return; // ok
        }
        errors.rejectValue("name","","Пользователь с таким логином уже есть");
    }


    public void validate_email(Object o, Errors errors){
        User user=(User) o;
        try{
            securityUserDetailsService.loadUserByEmail(user.getEmail());
        }
        catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("email","","Пользователь с таким email уже есть");

    }

    public void validate_password(Object o, Errors errors){

        User user=(User) o;
        String existingPassword= user.getPassword();
        String dbPassword=userService.getUserByName(user.getName()).getPassword();

        if(!passwordEncoder.matches(existingPassword,dbPassword)){
            errors.rejectValue("password","","Старый пароль неверный!");
        }
        else {return;}
    }

    public void validate_new_password(Object o, Errors errors){

        User user=(User) o;
        String new_password= user.getNew_password();

        if(new_password.length()<3){
            errors.rejectValue("new_password","","Пароль не может быть меньше 3-х символов!");
        }
        else {return;}

    }





}
