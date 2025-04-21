package ee.ivkhkdev.articles.config;

import ee.ivkhkdev.articles.service.AuthorDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthorDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/", "/authors/create","/error", "/login", "/webjars/**","tags","search","/articles/{id}","/authors/{id}","/tags/{id}/articles","/logout").permitAll()
                                .requestMatchers("/authors", "/authors/{id}/edit", "/authors/{id}/delete"). hasRole("ADMIN")

                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.usernameParameter("username")
                                .loginPage("/login")
                                .defaultSuccessUrl("/")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/articles/create", "/authors/create", "/tags/create", "/articles/{id}/delete", "/tags/{id}/delete", "/authors/{id}/delete", "/articles/{id}/update", "/tags/{id}/update", "/authors/{id}/update", "/articles/search", "/tags/{id}/articles","/logout")
                );

        return http.build();
    }
}