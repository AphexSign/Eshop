package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yarm.eshop5.DTO.CartDTO;
import ru.yarm.eshop5.Models.Cart;
import ru.yarm.eshop5.Services.CartService;
import ru.yarm.eshop5.Services.ProductService;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }


    //Показать всю корзину
    @GetMapping
    public String showCart(Model model, Principal principal) {
        if(principal == null){
            model.addAttribute("cart", new Cart());
        }
        else {
            CartDTO cartDTO=cartService.getCartByUser(principal.getName());
            model.addAttribute("cart", cartDTO);
        }
        return "cart";
    }

    //Убирает все товары из корзины
    @GetMapping("/{id}/remove")
    public String removeFromCart(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/cart";
        }

        cartService.removeFromUserCart(id,principal.getName());
        return "redirect:/cart";
    }


    @GetMapping("/{id}/add")
    public String addToCart(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/cart";
        }

        productService.addToUserCart(id,principal.getName());

        return "redirect:/cart";
    }


    @GetMapping("/{id}/minus")
    public String minusToCart(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/cart";
        }

        cartService.minusFromUserCart(id,principal.getName());

        return "redirect:/cart";
    }





    @PostMapping
    public String performOrder(@RequestParam(name = "payment") String payment, Principal principal){
        if(principal != null){
            cartService.commitCartToOrder(principal.getName(),payment);
        }
        return "redirect:/cart";
    }

}
