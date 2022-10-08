package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Repositories.OrderRepository;
import ru.yarm.eshop5.Services.OrderService;

import java.util.List;

@Controller
@RequestMapping("/order_admin")
public class OrderAdminController {
    //Есть только право доставить заказ
    private final OrderService orderService;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Вывести абсолютно все заказы, всех пользователей со статусом
    @GetMapping
    public String showOrder(Model model) {
        List<Order> orderList=orderService.getAllOrdersSortedById();
        model.addAttribute("orders", orderList);
        return "order_admin";
    }

    @GetMapping("/{id}/deliver")
    public String deliverOrder(@PathVariable Long id) {
        orderService.deliverOrder(id);
        return "redirect:/order_admin";
    }




}
