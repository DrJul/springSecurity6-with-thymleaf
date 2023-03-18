package com.example.securitywiththymeleaf.controller.webController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')") // when using @EnableMethodSecurity in SecurityConfig.class
    @GetMapping("/adminPage")
    public String getAdminPage(){
        return "adminPage";
    }

    @GetMapping("/userPage")
    public String getUserPage(){
        return "userPage";
    }
}
