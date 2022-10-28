package ru.yarm.eshop5.Services;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Models.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Product product=(Product) o;

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try{
           LocalDate man=LocalDate.parse(product.getStr_manufacture(),formatter);

        }
        catch (Exception ignored){
            errors.rejectValue("str_manufacture","","Формат даты неверен");
        }

        try{
            LocalDate exp=LocalDate.parse(product.getStr_expire(),formatter);
        }
        catch (Exception ignored){
            errors.rejectValue("str_expire","","Формат даты неверен");
        }

        try{
            if(LocalDate.parse(product.getStr_manufacture(),formatter).isAfter(LocalDate.parse(product.getStr_expire(),formatter))){
                errors.rejectValue("str_manufacture","","Нарушена логика даты");
            }
        }
        catch (Exception ignored){

        }







    }

}
