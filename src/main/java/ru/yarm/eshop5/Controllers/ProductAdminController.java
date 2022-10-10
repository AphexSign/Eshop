package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Repositories.ProductRepository;
import ru.yarm.eshop5.Services.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/prod_admin")
public class ProductAdminController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductAdminController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    @GetMapping
    public String registProduct(@ModelAttribute("product") Product product, Model model) {
        //Выдать по сортировке
        List<Product> products = productRepository.findAllByOrderByIdAsc();
        model.addAttribute("products", products);
        return "prod_admin";
    }

    @PostMapping
    public String performRegist(@ModelAttribute("product") @Valid Product product,
                                BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) return "prod_admin";
        productService.addProductToDB(product);
        return "redirect:/prod_admin";
    }


    @GetMapping("/{id}/deactive")
    public String deactiveProd(@PathVariable Long id) {
        productService.deactiveProd(id);
        return "redirect:/prod_admin";
    }

    @GetMapping("/{id}/active")
    public String activProd(@PathVariable Long id) {
        productService.activeProd(id);
        return "redirect:/prod_admin";
    }


}
