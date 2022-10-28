package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.OrderDetails;
import ru.yarm.eshop5.Services.OrderDetailService;
import ru.yarm.eshop5.Services.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public OrderController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
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

    @GetMapping("orders/{id}/details")
    public String showInfoOrder(@PathVariable Long id, Model model, Principal principal) {

        if(principal == null){
            model.addAttribute("orderDetails", new OrderDetails());
        }
        else {
            List<OrderDetails> orderDetailsList=orderDetailService.findAllOrderDetailsByOrderMy(id, principal);
            model.addAttribute("orderDetails",orderDetailsList);
        }

        return "order_detail_my";




    }





}
