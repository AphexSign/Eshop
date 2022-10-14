package ru.yarm.eshop5.Controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Services.ProductService;

import javax.validation.Valid;

@Controller
public class ProductInfoController {

    private final ProductService productService;

    public ProductInfoController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product_info")
    public String editProdSubmit(@ModelAttribute("product") @Valid Product product,
                                 BindingResult bindingResult)
    {
        
        if (bindingResult.hasErrors()) return "product_info";
        productService.updateProductToDB(product);
        return "redirect:/prod_admin";
    }



}
