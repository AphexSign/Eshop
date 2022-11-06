package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void send(){
        SimpleMailMessage mailMessage=new SimpleMailMessage();

        String emailTo="alex130718@mail.ru";
        String subject="Activation";
        String message="Message";

        mailMessage.setFrom("uzi730@yandex.ru");
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }


}
