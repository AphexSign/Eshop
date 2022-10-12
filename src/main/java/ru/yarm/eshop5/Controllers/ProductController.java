package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Repositories.ProductRepository;
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

    //Сделать показ только активных продуктов, формировать только тот список из ID, выдача артикула
    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productService.getProductActive();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}/cart")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserCart(id,principal.getName());
        return "redirect:/products";
    }

}