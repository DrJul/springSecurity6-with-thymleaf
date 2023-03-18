package com.example.securitywiththymeleaf.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // we can also add in memory user
    @Bean
    public UserDetailsService createInMemoryUser(){
        UserDetails defaultUser = User.builder() // notice this User is from spring security package
                .username("userFromSpringSecurityCore")
                .password(passwordEncoder().encode("pass")) // must be encoded here
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(defaultUser);
    }
}
