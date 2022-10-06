package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yarm.eshop5.DTO.CartDTO;
import ru.yarm.eshop5.Models.Cart;
import ru.yarm.eshop5.Services.CartService;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
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

    //Убирает из корзины товар
    @GetMapping("/{id}/remove")
    public String removeFromCart(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/cart";
        }
        System.err.println("Press button remove from cart Product:"+id+" user:"+principal.getName());
        //Вносит ID-товара к текущему User и не позволяет перейти в саму корзину, возвращает нас назад
        cartService.removeFromUserCart(id,principal.getName());
        return "redirect:/cart";
    }


    @PostMapping
    public String performOrder(Principal principal){
        if(principal != null){
            cartService.commitCartToOrder(principal.getName());
        }
        return "redirect:/cart";
    }



}
