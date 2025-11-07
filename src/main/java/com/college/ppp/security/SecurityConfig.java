package com.college.ppp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**").permitAll()
                .requestMatchers("/partners", "/projects", "/projects/*").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", true)
            )
            .logout(Customizer.withDefaults)
            .csrf(Customizer.withDefaults);
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
        manager.createUser(User.withUsername("coord").password("coord").roles("COORDINATOR").build());
        manager.createUser(User.withUsername("viewer").password("viewer").roles("VIEWER").build());
        return manager;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        // For demo simplicity; replace with BCryptPasswordEncoder in production.
        return NoOpPasswordEncoder.getInstance();
    }
}