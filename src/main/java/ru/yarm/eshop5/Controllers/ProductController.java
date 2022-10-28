package ru.yarm.eshop5.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yarm.eshop5.Models.Category;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Services.CategoryService;
import ru.yarm.eshop5.Services.ProductService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
   // @ModelAttribute("product") Product product, @ModelAttribute("category")Category category,
    //Сделать показ только активных продуктов, формировать только тот список из ID, выдача артикула
    @GetMapping
    public String showProducts(@RequestParam(name="category",required = false) Long id_cat, Model model) {

        List<Product> tmplist=new ArrayList<>();
        System.err.println(id_cat);
        if(id_cat!=null){
          tmplist = productService.getProductsByCategory(id_cat);
          model.addAttribute("variable",id_cat);
        }
        else {
            tmplist = productService.getProductActive();
        }

        model.addAttribute("products", tmplist);
        model.addAttribute("categories", categoryService.getAllCategorySortedById());
        return "products";
    }

    @GetMapping("/{id}/cart")
    public String addCart(@PathVariable Long id,
                            @RequestParam(name="category",required = false) Long id_cat,Principal principal, Model model,
                            RedirectAttributes redirectAttrs) {
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserCart(id,principal.getName());

       // redirectAttrs.addAttribute("id_cat",id_cat);
        return "redirect:/products";
    }

    @PostMapping
    public String searchByCategory(@RequestParam(name="category") Long id, Model model)
    {
        model.addAttribute("products", productService.getProductsByCategory(id));
        model.addAttribute("categories", categoryService.getAllCategorySortedById());
        return "products";
    }

}