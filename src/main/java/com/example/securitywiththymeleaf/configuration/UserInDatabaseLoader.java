package com.example.securitywiththymeleaf.configuration;

import com.example.securitywiththymeleaf.entity.UserRole;
import com.example.securitywiththymeleaf.entity.UserEntity;
import com.example.securitywiththymeleaf.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Class only for development, loads user at database when running app
 */
@Component
@AllArgsConstructor
public class UserInDatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        UserEntity adminToSave = new UserEntity();
        adminToSave.setEmail("adminFromDb");
        adminToSave.setPassword( new BCryptPasswordEncoder().encode("pass"));
        adminToSave.setRole(UserRole.ADMINISTRATOR.getRole());
        userRepository.save(adminToSave);

        UserEntity userToSave = new UserEntity();
        userToSave.setEmail("userFromDb");
        userToSave.setPassword( new BCryptPasswordEncoder().encode("pass"));
        userToSave.setRole(UserRole.DEVELOPER.getRole());
        userRepository.save(userToSave);
    }
}
