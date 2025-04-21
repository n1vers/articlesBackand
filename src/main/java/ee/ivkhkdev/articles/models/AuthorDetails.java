package ee.ivkhkdev.articles.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthorDetails implements UserDetails {

    private final Author author;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthorDetails(Author author, Collection<? extends GrantedAuthority> authorities) {
        this.author = author;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return author.getPassword();
    }

    @Override
    public String getUsername() {
        return author.getUsername();
    }

    public static AuthorDetails build(Author author) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(author.getRole().name()));
        return new AuthorDetails(author, authorities);
    }
}