package ee.ivkhkdev.articles.controller;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/")
    public String viewHome(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "security/login";
    }

    @GetMapping("/search")
    public String searchArticles(@RequestParam("query") String query, Model model) {
        List<Article> articles = articleService.searchArticles(query);
        model.addAttribute("articles", articles);
        return "index";
    }
}