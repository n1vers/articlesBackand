package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Article;

import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<Article> findAll();
    Optional<Article> findById(Long id);
    Article save(Article article);
    Article update(Long id,Article article);
    void deleteById(Long id);

}
