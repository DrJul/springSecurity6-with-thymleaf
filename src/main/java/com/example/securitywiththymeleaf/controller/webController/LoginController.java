package com.example.securitywiththymeleaf.controller.webController;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/", "/index"})
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //few different annotiations for authentication / @EnabledMethodSecurity must be added in SpringConfig.class
//    @PreAuthorize("someMethod()")
//    @PreAuthorize("ROLE_ADMIN")
//    @Secured("ROLE_ADMIN")
//    @RolesAllowed("ADMIN")
    @GetMapping("/adminPage")
    public String getAdminPage(){
        return "adminPage";
    }

    @GetMapping("/userPage")
    public String getUserPage(){
        return "userPage";
    }
}
