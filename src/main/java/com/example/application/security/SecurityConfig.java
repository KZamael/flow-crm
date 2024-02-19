package com.example.application.security;

import com.example.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // <1>
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll()); // <3>
        super.configure(http);
        setLoginView(http, LoginView.class); // <4>
    }

    /** Not for use in production! There your Pw should be ecnrypted **/
    /*@Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.withUsername("user")
                .password("{noop}userpass")
                .roles(USER_ROLE)
                .build());
    }*/

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                // password = userpass with this hash, don't tell anybody ;-)
                .password("{bcrypt}$2a$04$cTkDAgothZDPVONkg3caWeWEyd.bmX6dGALtIzWLeoMJZZou7C.Ky")
                .roles(USER_ROLE)
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$04$cTkDAgothZDPVONkg3caWeWEyd.bmX6dGALtIzWLeoMJZZou7C.Ky")
                .roles(USER_ROLE, ADMIN_ROLE)
                .build();
        return new InMemoryUserDetailsManager(user, admin); // <5>
    }
}
