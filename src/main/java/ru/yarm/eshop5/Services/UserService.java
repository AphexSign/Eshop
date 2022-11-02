package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
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



    public User getUserByName(String name){
        return userRepository.findByName(name).get();
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

    @Transactional
    public void delete(Long id) {
        //Удалить все OrderDetails, orders
        userRepository.delete(userRepository.getById(id));
    }
}
