package ee.ivkhkdev.articles.service;

import ee.ivkhkdev.articles.models.Author;
import ee.ivkhkdev.articles.models.AuthorDetails;
import ee.ivkhkdev.articles.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorDetailsService implements UserDetailsService {
    @Autowired
    private  AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        author.setPassword(author.getPassword().trim());
        System.out.println(author.getUsername() + ' ' + author.getEmail() + ' ' + author.getPassword());
        return AuthorDetails.build(author);
    }
}
