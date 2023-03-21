package com.example.securitywiththymeleaf.configuration;


import com.example.securitywiththymeleaf.entity.UserRole;
import com.example.securitywiththymeleaf.service.imlp.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
//    private BCryptPasswordEncoder encoder;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toH2Console()).permitAll() // this h2 path is taken form application.properties
                .requestMatchers("/userPage").hasAnyAuthority(UserRole.DEVELOPER.getRole(),UserRole.ADMINISTRATOR.getRole())
                .requestMatchers("/adminPage").hasAuthority(UserRole.ADMINISTRATOR.getRole()) // watch out 'ROLE_" prefix
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

//    // we can also add in memory user
//    @Bean
//    public UserDetailsService createInMemoryUser(){
//        UserDetails defaultUser = User.builder() // notice this User is from spring security package
//                .username("userFromSpringSecurityCore")
//                .password(passwordEncoder().encode("pass")) // must be encoded here
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(defaultUser);
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }


//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ROLE_ADMIN > ROLE_MODERATOR \n ROLE_MODERATOR > ROLE_USER";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }
//    @Autowired
//    public void configureGlobal(AuthenticationManager authenticationManager){
//        authenticationManager.
//    }
}
