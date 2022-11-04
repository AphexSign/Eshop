package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.yarm.eshop5.Models.Role;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CLIENT);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user){

        User userToUpdate=userRepository.getReferenceById(user.getId());
       // user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRole(Role.CLIENT);
        userToUpdate.setFio(user.getFio());
     //   userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setTelephone(user.getTelephone());

    }

    @Transactional
    public void updateUser_login(User user) {
        User userToUpdate=userRepository.getReferenceById(user.getId());
        userToUpdate.setName(user.getName());

    }
    @Transactional
    public void updateUser_email(User user) {

        User userToUpdate=userRepository.getReferenceById(user.getId());
        userToUpdate.setEmail(user.getEmail());

    }

    @Transactional
    public void updateUser_password(User user) {
        User userToUpdate=userRepository.getReferenceById(user.getId());
       // String new_password=user.getNew_password();
        userToUpdate.setPassword(passwordEncoder.encode(user.getNew_password()));
    }


}
