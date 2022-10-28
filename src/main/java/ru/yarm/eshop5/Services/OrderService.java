package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.OrderDetails;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderCrudRepository orderCrudRepository;
    private final Order_StatusRepository order_statusRepository;
    private final OrderDetailRepository orderDetailRepository;




    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderCrudRepository orderCrudRepository, Order_StatusRepository order_statusRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderCrudRepository = orderCrudRepository;
        this.order_statusRepository = order_statusRepository;
        this.orderDetailRepository = orderDetailRepository;

    }

    //Выдача несортированного списка Заказов
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
//        order.setStatus(OrderStatus.PAID);
        order.setOrder_status(order_statusRepository.getReferenceById(4L));
        order.setChanged(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long id, String name) {
        User user=userRepository.findByName(name).get();
        Order order=orderRepository.getReferenceById(id);
//        order.setStatus(OrderStatus.CANCELLED);
        order.setOrder_status(order_statusRepository.getReferenceById(3L));
        order.setChanged(LocalDateTime.now());
        orderRepository.save(order);
    }

    //Выдача сортированного списка
    public List<Order> getOrderByUserAndSortById(String name) {
        List<Order> orders=getOrderByUser(name);
        Comparator<Order> comparator = new Comparator<Order>() {
            @Override
            public int compare(Order left, Order right) {
                return Long.compare(left.getId(),right.getId());
            }
        };
        Collections.sort(orders, comparator);
        orders.sort(comparator);
        return orders;
    }

    public List<Order> getAllOrdersSortedById() {
        List<Order> orders=orderRepository.findAll();
        Comparator<Order> comparator = new Comparator<Order>() {
            @Override
            public int compare(Order left, Order right) {
                return Long.compare(left.getId(),right.getId());
            }
        };
        Collections.sort(orders, comparator);
        orders.sort(comparator);
        return orders;
    }

    @Transactional
    public void deliverOrder(Long id) {
        Order order=orderRepository.getById(id);
        order.setOrder_status(order_statusRepository.getReferenceById(5L));
        order.setChanged(LocalDateTime.now());
        orderRepository.save(order);
    }

    public Order getOrderById(Long id){
       return orderRepository.getReferenceById(id);
    }


    @Transactional
    public void deleteOrder(Long id){

        List<OrderDetails> orderDetailsList=getOrderById(id).getDetails();
        System.err.println(orderDetailsList.size());

        for(OrderDetails orderDetails:orderDetailsList){
            orderDetailRepository.deleteById(orderDetails.getId());
        }

        orderRepository.deleteById(id);
        }

    }






