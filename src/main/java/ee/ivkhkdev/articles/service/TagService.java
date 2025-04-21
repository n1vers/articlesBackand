package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Tag;
import ee.ivkhkdev.articles.repository.ArticleRepository;
import ee.ivkhkdev.articles.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements ITagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Long id) {
        Tag tag = findByIdOrThrow(id);
        List<Article> articles = articleRepository.findByTagsContaining(tag);
        for (Article article : articles) {
            article.getTags().remove(tag);
            articleRepository.save(article);
        }
        tagRepository.deleteById(id);
    }

    public Tag findByIdOrThrow(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }
}
