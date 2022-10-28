package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Services.CategoryService;
import ru.yarm.eshop5.Services.ProductService;
import ru.yarm.eshop5.Services.ProductValidator;

import javax.validation.Valid;

@Controller
public class ProductInfoController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductValidator productValidator;

    public ProductInfoController(ProductService productService, CategoryService categoryService, ProductValidator productValidator) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productValidator = productValidator;
    }

    @PostMapping("/product_info")
    public String editProdSubmit(@ModelAttribute("product") @Valid Product product,
                                 BindingResult bindingResult, Model model)
    {
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories",categoryService.getAllCategorySortedById());
            return "product_info";}

        productService.updateProductToDB(product);
        return "redirect:/prod_admin";
    }









}
