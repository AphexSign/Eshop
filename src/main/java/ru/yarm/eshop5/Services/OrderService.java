package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.OrderStatus;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.OrderCrudRepository;
import ru.yarm.eshop5.Repositories.OrderRepository;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderCrudRepository orderCrudRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderCrudRepository orderCrudRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderCrudRepository = orderCrudRepository;
    }

    public List<Order> getOrderByUser(String userName){
        User user=userRepository.findByName(userName).get();
        List<Order> orderList=new ArrayList<>();
        orderList=orderCrudRepository.findAllByUser(user);
        return orderList;
    }

    @Transactional
    public void payOrder(Long id, String name) {
        User user=userRepository.findByName(name).get();
        Order order=orderRepository.getReferenceById(id);
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long id, String name) {
        User user=userRepository.findByName(name).get();
        Order order=orderRepository.getReferenceById(id);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }


}
