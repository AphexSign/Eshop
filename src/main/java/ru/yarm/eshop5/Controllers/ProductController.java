package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Services.ProductService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productService.getAll();
        System.out.println(products.size());
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}/cart")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/products";
        }
        System.err.println("Press button add to bucket Product:"+id+" user:"+principal.getName());
        //Вносит ID-товара к текущему User и не позволяет перейти в саму корзину, возвращает нас назад
        productService.addToUserCart(id,principal.getName());
        return "redirect:/products";
    }
}