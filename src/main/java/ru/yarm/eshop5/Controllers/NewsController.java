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
import ru.yarm.eshop5.Models.News;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Services.NewsService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("/news_admin")
    public String showNews(@ModelAttribute("news")News news, Model model, Principal principal) {
        if(principal == null){
        }
        else {
           // List<News> newsList=newsService.getAllNewsAndSortById();
            List<News> newsList=newsService.getAllNewsAndSortByIdAsc();
            model.addAttribute("newslist",newsList);

        }
        return "news_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping("/news_admin")
    public String RegisterCategory(@ModelAttribute("news") News news, Model model,
                                   Principal principal)
    {
        newsService.addNewsToDB(news, principal);
        return "redirect:/news_admin";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("news_admin/{id}/deactive")
    public String deactiveNews(@PathVariable Long id) {
        newsService.deactiveNews(id);
        return "redirect:/news_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("news_admin/{id}/active")
    public String activeProd(@PathVariable Long id) {
        newsService.activeNews(id);
        return "redirect:/news_admin";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("news_admin/{id}/edit")
    public String editProd(@PathVariable Long id, @ModelAttribute("news") News news, Model model) {
        News news1=newsService.getProductByID(id);

        model.addAttribute("news", news1);
        return "news_info";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping("news_admin/{id}/delete")
    public String deleteProd(@PathVariable Long id) {
        newsService.deleteNews(id);
        return "redirect:/news_admin";
    }


    @PostMapping("/news_info")
    public String editNewsSubmit(@ModelAttribute("news") News news,
                                 BindingResult bindingResult, Model model)
    {
       // productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            return "news_info";}

        newsService.updateNewsToDB(news);
        return "redirect:/news_admin";
    }













}
