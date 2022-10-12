package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.OrderRepository;
import ru.yarm.eshop5.Services.OrderService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Показать всю корзину, с сортировкой
    @GetMapping("/orders")
    public String showOrder(Model model, Principal principal) {
        if(principal == null){
            model.addAttribute("order", new Order());
        }
        else {
            List<Order> orders=orderService.getOrderByUserAndSortById(principal.getName());
            model.addAttribute("orders", orders);
        }
        return "orders";
    }

    @GetMapping("/orders/{id}/pay")
    public String payOrder(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/orders";
        }
        orderService.payOrder(id,principal.getName());
        return "redirect:/orders";
    }

    @GetMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/orders";
        }
        orderService.cancelOrder(id,principal.getName());
        return "redirect:/orders";
    }






}
