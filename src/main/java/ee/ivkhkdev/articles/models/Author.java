package ee.ivkhkdev.articles.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false,length = 30)
    private String email;

    @Column(nullable = false,length = 30)
    private String username;

    @Column(nullable = true,length = 200)
    private String imageUrl;

    @Column(nullable = false,length = 200)
    private String password;

    @Column(nullable = true,length = 500)
    private String bio;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    private Roles role = Roles.ROLE_USER;

    public Author() {
    }

    public Author(Long id, LocalDateTime createdAt, String email, String username, String imageUrl, String password, String bio, List<Article> articles, Roles role) {
        this.id = id;
        this.createdAt = createdAt;
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
        this.password = password;
        this.bio = bio;
        this.articles = articles;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(createdAt, author.createdAt) && Objects.equals(email, author.email) && Objects.equals(username, author.username) && Objects.equals(imageUrl, author.imageUrl) && Objects.equals(password, author.password) && Objects.equals(bio, author.bio) && Objects.equals(articles, author.articles) && role == author.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, email, username, imageUrl, password, bio, articles, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", articles=" + articles.stream()
                .map(article -> article.getTitle()) // Можно заменить на getId()
                .toList() +
                '}';
    }
}
