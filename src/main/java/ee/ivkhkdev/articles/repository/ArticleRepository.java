package ee.ivkhkdev.articles.repository;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContainingIgnoreCase(String title);
    List<Article> findByTagsContaining(Tag tag);
}