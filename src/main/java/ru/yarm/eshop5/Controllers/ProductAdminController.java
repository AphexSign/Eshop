package ru.yarm.eshop5.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yarm.eshop5.Models.Category;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Services.CategoryService;
import ru.yarm.eshop5.Services.ProductService;
import ru.yarm.eshop5.Services.ProductValidator;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/prod_admin")
public class ProductAdminController {

    private final ProductService productService;
    private final ProductValidator productValidator;
    private final CategoryService categoryService;

    public ProductAdminController(ProductService productService, ProductValidator productValidator, CategoryService categoryService) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping
    public String registerProduct(@ModelAttribute("product") Product product, @ModelAttribute("category")Category category, Model model) {

        model.addAttribute("products",productService.getAllByOrderByIdAsc());
        model.addAttribute("categories",categoryService.getAllCategorySortedById());
        return "prod_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping
    public String performRegist(@ModelAttribute("product") @Valid Product product,
                                BindingResult bindingResult, Model model)
    {

        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("products",productService.getAllByOrderByIdAsc());
            model.addAttribute("categories",categoryService.getAllCategorySortedById());
            return "prod_admin";
        }

        productService.addProductToDB(product);
        return "redirect:/prod_admin";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/deactive")
    public String deactiveProd(@PathVariable Long id) {
        productService.deactiveProd(id);
        return "redirect:/prod_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/active")
    public String activeProd(@PathVariable Long id) {
        productService.activeProd(id);
        return "redirect:/prod_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/edit")
    public String editProd(@PathVariable Long id, @ModelAttribute("product") Product product, @ModelAttribute("category")Category category, Model model) {
        Product product1=productService.getProductByID(id);
        List<Category> categories=categoryService.getAllCategorySortedById();

        model.addAttribute("product", product1);
        model.addAttribute("categories", categories);
        return "product_info";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/{id}/delete")
    public String deleteProd(@PathVariable Long id) {
        productService.deleteProd(id);
        return "redirect:/prod_admin";
    }

    //Сделать новостную ленту внутри магазина

}
