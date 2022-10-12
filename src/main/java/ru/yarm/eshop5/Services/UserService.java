package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.OrderStatus;
import ru.yarm.eshop5.Models.Role;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void banUser(Long id) {
        User user=userRepository.getReferenceById(id);
        user.setArchive(true);
        user.setRole(Role.BANNED);
        userRepository.save(user);
    }


    @Transactional
    public void unbanUser(Long id) {
        User user=userRepository.getReferenceById(id);
        user.setArchive(false);
        user.setRole(Role.CLIENT);
        userRepository.save(user);
    }

    @Transactional
    public void mk_manager(Long id) {
        User user=userRepository.getReferenceById(id);
        user.setArchive(false);
        user.setRole(Role.MANAGER);
        userRepository.save(user);
    }

    public List<User> getAllByOrderByIdAsc() {
     return  userRepository.findAllByOrderByIdAsc();
    }
}
