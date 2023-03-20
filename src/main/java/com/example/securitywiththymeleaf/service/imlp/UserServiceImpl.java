package com.example.securitywiththymeleaf.service.imlp;

import com.example.securitywiththymeleaf.entity.UserEntity;
import com.example.securitywiththymeleaf.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("Not found");
        }
        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole())
                .build();
    }

//    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> userRoles) {
//        Set<GrantedAuthority> authorities=new HashSet<>();
//        for (UserRole userRole : userRoles) {
//            authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
//        }
//        return authorities;
//    }
}
