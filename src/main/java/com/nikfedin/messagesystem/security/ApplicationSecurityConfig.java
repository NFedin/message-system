package com.nikfedin.messagesystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        http.csrf().disable();
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails elvisUser = User.builder()
                .username("elvispresley")
                .password(passwordEncoder.encode("pass123"))
                .roles("USER")
                .build();

        UserDetails bobUser = User.builder()
                .username("bobdylan")
                .password(passwordEncoder.encode("pass123"))
                .roles("USER")
                .build();
        UserDetails mickUser = User.builder()
                .username("mickjagger")
                .password(passwordEncoder.encode("pass123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(
                elvisUser, bobUser, mickUser
        );
    }
}
