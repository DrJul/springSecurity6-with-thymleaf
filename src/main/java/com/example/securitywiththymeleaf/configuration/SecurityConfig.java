package com.example.securitywiththymeleaf.configuration;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.nio.file.Path;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toH2Console()).permitAll() // this h2 path is taken form application.properties
                .anyRequest().authenticated()
                .and()
                .csrf()
                .ignoringRequestMatchers(PathRequest.toH2Console()) //strongly not recommended for production code
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
//                .httpBasic(Customizer.withDefaults())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index")
                                .permitAll()
                ).logout(
                        logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );


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
