package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yarm.eshop5.Models.User;


@Component
public class UserValidator implements Validator {

    private final SecurityUserDetailsService securityUserDetailsService;
    @Autowired
    public UserValidator(SecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService= securityUserDetailsService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    //Проверка на отсутствие двойников в БД
    @Override
    public void validate(Object o, Errors errors) {
        User user=(User) o;
        try {
            securityUserDetailsService.loadUserByUsername(user.getName());
        }
        catch (UsernameNotFoundException ignored){
            return; // ok
        }
        errors.rejectValue("name","","Пользователь с таким логином уже есть");
    }
}
