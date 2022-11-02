package ru.yarm.eshop5.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.OrderDetails;
import ru.yarm.eshop5.Services.OrderDetailService;
import ru.yarm.eshop5.Services.OrderService;

import java.util.List;

@Controller
@RequestMapping("/order_admin")
public class OrderAdminController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public OrderAdminController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping
    public String showOrder(Model model) {
        List<Order> orderList=orderService.getAllOrdersSortedById();
        model.addAttribute("orders", orderList);


        return "order_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/deliver")
    public String deliverOrder(@PathVariable Long id) {
        orderService.deliverOrder(id);
        return "redirect:/order_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/details")
    public String showInfoOrder(@PathVariable Long id, Model model) {
        List<OrderDetails> orderDetailsList=orderDetailService.findAllOrderDetailsByOrder(id);
        model.addAttribute("orderDetails",orderDetailsList);
        return "order_info";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order_admin";
    }



}
