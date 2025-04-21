package ee.ivkhkdev.articles.utils;

import com.github.javafaker.Faker;
import ee.ivkhkdev.articles.models.Article;
import ee.ivkhkdev.articles.models.Author;
import ee.ivkhkdev.articles.models.Roles;
import ee.ivkhkdev.articles.models.Tag;
import ee.ivkhkdev.articles.repository.ArticleRepository;
import ee.ivkhkdev.articles.repository.TagRepository;
import ee.ivkhkdev.articles.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class InsertData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final Faker faker;
    private final Random random;

    public InsertData(AuthorRepository authorRepository, ArticleRepository articleRepository, TagRepository tagRepository) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.faker = new Faker();
        this.random = new Random();
    }

    @Override
    public void run(String... args) throws Exception {
                seedUsers();
                seedTags();
                seedArticles();
                Author admin =new Author();
                admin.setCreatedAt(LocalDateTime.now());
                admin.setEmail("admin@mail.com");
                admin.setUsername("admin");
                admin.setImageUrl("https://example.com/image.jpg");
                admin.setPassword(new BCryptPasswordEncoder().encode("password123"));
                admin.setRole(Roles.ROLE_ADMIN);
                admin.setBio("admin");
                authorRepository.save(admin);

                Author user =new Author();
                user.setCreatedAt(LocalDateTime.now());
                user.setEmail("user@mail.com");
                user.setUsername("user");
                user.setImageUrl("https://example.com/image.jpg");
                user.setPassword(new BCryptPasswordEncoder().encode("password123"));
                user.setRole(Roles.ROLE_USER);
                user.setBio("user");
                authorRepository.save(user);
    }



    private void seedUsers() {
        for (int i = 0; i < 5; i++) {
            Author user = new Author();
            user.setCreatedAt(LocalDateTime.now());
            user.setEmail(faker.internet().emailAddress());
            user.setUsername(faker.name().username());
            user.setImageUrl(faker.internet().avatar());
            user.setPassword(new BCryptPasswordEncoder().encode("password123"));
            user.setBio(faker.lorem().paragraph());
            user.setRole(Roles.ROLE_USER);
            authorRepository.save(user);
        }
    }

    private void seedTags() {
        String[] programmingTags = {"Java", "Spring", "Python", "JavaScript", "Docker"};

        for (String tagName : programmingTags) {
            Tag tag = new Tag();
            tag.setCreatedAt(LocalDateTime.now());
            tag.setName(tagName);
            tagRepository.save(tag);
        }
    }
    private void seedArticles() {
        List<Author> users = authorRepository.findAll();
        List<Tag> tags = tagRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setCreatedAt(LocalDateTime.now());
            article.setUpdatedAt(LocalDateTime.now());
            article.setTitle(faker.book().title());
            article.setSlug(article.getTitle().toLowerCase().replaceAll("[^a-z0-9\\s]", "")
                                                            .replaceAll("\\s+", "-") + "-" +i);
            article.setContent(faker.lorem().paragraph(5));

            String generatedDescription = faker.lorem().sentence();
            int maxDescriptionLength = 50;
            if (generatedDescription.length() > maxDescriptionLength) {
                generatedDescription = generatedDescription.substring(0, maxDescriptionLength);
            }
            article.setDescription(generatedDescription);


            article.setTags(List.of(tags.get(random.nextInt(tags.size()))));


            article.setUser(users.get(random.nextInt(users.size())));

            articleRepository.save(article);
        }
    }

//    private void searchArticlesByKeyword(String keyword) {
//        List<Article> articles = articleRepository.findAllByKeyword(keyword);
//        System.out.println("==============================");
//        System.out.println("Searching for articles with keyword: " + keyword);
//        if (!articles.isEmpty()) {
//            System.out.println("Found " + articles.size() + " articles:");
//            for (Article article : articles) {
//                System.out.println(" - " + article.getTitle());
//            }
//        } else {
//            System.out.println("No articles found with keyword: " + keyword);
//        }
//        System.out.println("==============================");
//    }
//
//    private void searchArticleByTags(String tagName) {
//        List<Article> articles = articleRepository.findByTags_Name(tagName);
//        System.out.println("==============================");
//        System.out.println("Searching for articles with tag: " + tagName);
//        if (!articles.isEmpty()) {
//            System.out.println("Found " + articles.size() + " articles:");
//            for (Article article : articles) {
//                System.out.println(" - " + article.getTitle());
//            }
//        } else {
//            System.out.println("No articles found with tag: " + tagName);
//        }
//        System.out.println("==============================");
//    }
//
//    private void searchTagByName(String tagName) {
//        List<Article> articles = articleRepository.findByTags_Name(tagName);
//        System.out.println("==============================");
//        System.out.println("Searching for articles with tag: " + tagName);
//        if (!articles.isEmpty()) {
//            System.out.println("Found " + articles.size() + " articles:");
//            for (Article article : articles) {
//                System.out.println(" - " + article.getTitle());
//            }
//        } else {
//            System.out.println("No articles found with tag: " + tagName);
//        }
//        System.out.println("==============================");
//    }
//
//    private void searchArticleByTittle(String title) {
//        List<Article> articles = articleRepository.findByTitle(title);
//        System.out.println("==============================");
//        System.out.println("Searching for articles with title: " + title);
//        if (!articles.isEmpty()) {
//            System.out.println("Found " + articles.size() + " articles:");
//            for (Article article : articles) {
//                System.out.println(" - " + article.getTitle());
//            }
//        } else {
//            System.out.println("No articles found with title: " + title);
//        }
//        System.out.println("==============================");
//    }
//
//    private void searchUserByDate(String date) {
//        LocalDateTime parsedDate = LocalDateTime.parse(date + "T00:00:00");
//        List<Author> authors = authorRepository.findByCreatedAtAfter(parsedDate);
//        System.out.println("==============================");
//        System.out.println("Searching for users created after: " + date);
//        if (!authors.isEmpty()) {
//            System.out.println("Found " + authors.size() + " users:");
//            for (Author author : authors) {
//                System.out.println(" - " + author.getUsername());
//            }
//        } else {
//            System.out.println("No users found created after: " + date);
//        }
//        System.out.println("==============================");
//    }
//
//    private void searchUserByEmail(String email) {
//        Author author = authorRepository.findByEmail(email);
//        System.out.println("==============================");
//        System.out.println("Searching for user with email: " + email);
//        if (author != null) {
//            System.out.println("Found user: " + author.getUsername() + " (" + author.getEmail() + ")");
//        } else {
//            System.out.println("User not found");
//        }
//        System.out.println("==============================");
//    }
//
//    private void searchArticleByUser(String username) {
//        List<Article> articles = articleRepository.findByUser_Username(username);
//        System.out.println("==============================");
//        System.out.println("Searching for articles by user: " + username);
//        if (!articles.isEmpty()) {
//            System.out.println("Found " + articles.size() + " articles:");
//            for (Article article : articles) {
//                System.out.println(" - " + article.getTitle());
//            }
//        } else {
//            System.out.println("No articles found for user: " + username);
//        }
//        System.out.println("==============================");
//    }
}
