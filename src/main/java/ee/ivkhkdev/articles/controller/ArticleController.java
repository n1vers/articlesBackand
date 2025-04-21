package ee.ivkhkdev.articles.controller;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Author;
import ee.ivkhkdev.articles.models.Tag;
import ee.ivkhkdev.articles.service.ArticleService;
import ee.ivkhkdev.articles.service.AuthorService;
import ee.ivkhkdev.articles.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final TagService tagService;
    private final ArticleService articleService;
    private final AuthorService authorService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");

    @Autowired
    public ArticleController(TagService tagService, ArticleService articleService, AuthorService authorService) {
        this.tagService = tagService;
        this.articleService = articleService;
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public String getArticleById(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        model.addAttribute("article", article);
        model.addAttribute("formattedDate", article.getCreatedAt().format(formatter));
        model.addAttribute("formattedUpdateDate", article.getUpdatedAt().format(formatter));
        return "articles/details";
    }

    @GetMapping("/create")
    public String showCreateArticleForm(Model model) {
        List<Tag> tags = tagService.getTags();
        List<Author> authors = authorService.findAll();
        model.addAttribute("article", new Article());
        model.addAttribute("tags", tags);
        model.addAttribute("authors", authors);
        return "articles/create";
    }

   @PostMapping("/create")
   public String createArticle(@ModelAttribute Article article) {
       Author currentAuthor = authorService.getCurrentAuthor();
       article.setUser(currentAuthor);
       article.setSlug(articleService.generateSlug(article.getTitle()));
       articleService.save(article);
       return "redirect:/";
   }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        List<Tag> tags = tagService.getTags();

        model.addAttribute("article", article);
        model.addAttribute("tags", tags);

        return "articles/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateArticle(@PathVariable Long id, @ModelAttribute Article article) {
        Author currentAuthor = authorService.getCurrentAuthor();
        Article existingArticle = articleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Статья не найдена"));

        if (!existingArticle.getUser().getId().equals(currentAuthor.getId())) {
            throw new RuntimeException("Вы не можете редактировать эту статью");
        }

        article.setId(id);
        article.setUser(currentAuthor);
        articleService.update(id, article);
        return "redirect:/";
    }

    @GetMapping("/{id}/delete")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteById(id);
        return "redirect:/";
    }

}
