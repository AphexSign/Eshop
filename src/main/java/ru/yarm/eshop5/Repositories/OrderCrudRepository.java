package ru.yarm.eshop5.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderCrudRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUser(User user);
    Optional<Order> findById(Long id);



}
