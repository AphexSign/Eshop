package ru.yarm.eshop5.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;
import ru.yarm.eshop5.Security.SecurityUserDetails;

import java.util.Optional;
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Реализуем спецаильный метод - прежде всего сам SecUserDetails,поскольку он заинтерфейсен
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByName(name);
        if(user.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new SecurityUserDetails(user.get());
    }


    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByEmail(email);
        if(user.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new SecurityUserDetails(user.get());
    }




}
