package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.OrderDetails;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Models.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private final OrderService orderService;


    public OrderDetailService(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<OrderDetails> findAllOrderDetailsByOrder(Long id){
        Order order=orderService.getOrderById(id);
        return order.getDetails();
    }

    public List<OrderDetails> findAllOrderDetailsByOrderMy(Long id, Principal principal){
        Order order=orderService.getOrderById(id);
        User receivedUser=order.getUser();

        if(receivedUser.getName().equals(principal.getName())){
            return order.getDetails();
        }
        else {
            return new ArrayList<OrderDetails>();
        }

    }



}
