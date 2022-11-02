package ru.yarm.eshop5.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.eshop5.Models.Category;
import ru.yarm.eshop5.Services.CategoryService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/category_admin")
    public String showCategory(@ModelAttribute("category") Category category, Model model) {
        List<Category> categories=categoryService.getAllCategorySortedById();
        model.addAttribute("categories",categories);
        return "category_admin";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping("/category_admin")
    public String RegisterCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories",categoryService.getAllCategorySortedById());
            return "category_admin";}

        categoryService.addCategoryToDB(category);
        return "redirect:/category_admin";
    }



    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("category_admin/{id}/edit")
    public String editCategory(@PathVariable Long id,@ModelAttribute("category")Category category, Model model) {
        Category category1=categoryService.getCategoryById(id);
        model.addAttribute("category", category1);
        return "category_info";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping("/category_info")
    public String editCategorySubmit(@ModelAttribute("category") @Valid Category category,
                                 BindingResult bindingResult)
    {

        if (bindingResult.hasErrors()) {
            return "category_info";}

        categoryService.updateCategoryToDB(category);
        return "redirect:/category_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("category_admin/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/category_admin";
    }







}
