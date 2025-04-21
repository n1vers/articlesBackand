package ee.ivkhkdev.articles.controller;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Tag;
import ee.ivkhkdev.articles.service.ArticleService;
import ee.ivkhkdev.articles.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    private final ArticleService articleService;

    public TagController(TagService tagService , ArticleService articleService) {
        this.tagService = tagService;
        this.articleService = articleService;
    }

    @GetMapping
    public String getTags(Model model) {
        model.addAttribute("tags", tagService.getTags());
        return "tags/list";
    }

    @GetMapping("/create")
    public String showCreateTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "tags/create";
    }
    @PostMapping("/create")
    public String createTag(@ModelAttribute Tag tag) {
        tagService.create(tag);
        return "redirect:/tags";
    }

    @GetMapping("/{id}/delete")
    public String deleteTag( @PathVariable Long id) {
        tagService.delete(id);
        return "redirect:/tags";
    }

    @GetMapping("/{id}/articles")
    public String getArticlesByTag(@PathVariable Long id, Model model) {
        Tag tag = tagService.findByIdOrThrow(id);
        List<Article> articles = articleService.findArticlesByTag(tag);
        model.addAttribute("tag", tag);
        model.addAttribute("articles", articles);
        return "tags/articles";
    }
}
