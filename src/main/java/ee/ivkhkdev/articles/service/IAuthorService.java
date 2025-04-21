package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author create(Author author);
    Author update(Long id,Author author);
    void delete(Long id);
}
