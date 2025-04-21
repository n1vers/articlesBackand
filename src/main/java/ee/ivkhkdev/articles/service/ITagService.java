package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Tag;

import java.util.List;

public interface ITagService {
    List<Tag> getTags();
    Tag create(Tag tag);
    void delete(Long id);
}
