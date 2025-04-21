package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Tag;
import ee.ivkhkdev.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article update(Long id, Article article) {
        return articleRepository.findById(id)
                .map(existingArticle -> {
                    existingArticle.setTitle(article.getTitle());
                    existingArticle.setSlug(generateSlug(article.getTitle()));
                    existingArticle.setContent(article.getContent());
                    existingArticle.setDescription(article.getDescription());
                    existingArticle.setTags(article.getTags());
                    return articleRepository.save(existingArticle);
                })
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }


    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9 ]", "") // убираем всё, кроме букв, цифр и пробелов
                .replaceAll("\\s+", "-"); // заменяем пробелы на дефисы
    }

    public List<Article> searchArticles(String title) {
        return articleRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Article> findArticlesByTag(Tag tag) {
        return articleRepository.findByTagsContaining(tag);
    }
}
