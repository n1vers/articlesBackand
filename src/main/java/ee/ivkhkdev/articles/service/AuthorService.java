package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Author;
import ee.ivkhkdev.articles.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Author create(Author author) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(author.getPassword());
        author.setPassword(encodedPassword);
        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        return authorRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(author.getEmail());
                    existingUser.setUsername(author.getUsername());
                    existingUser.setPassword(author.getPassword());
                    existingUser.setImageUrl(author.getImageUrl());
                    existingUser.setBio(author.getBio());
                    return authorRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("Could not find author with id: " + id));
    }

    public void delete(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    public Author getCurrentAuthor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return authorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Текущий пользователь не найден: " + username));
    }
}
