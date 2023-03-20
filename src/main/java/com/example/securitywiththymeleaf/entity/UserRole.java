package com.example.securitywiththymeleaf.entity;

public enum UserRole {
    ADMINISTRATOR("ROLE_ADMIN"), //hasAuthority(‘ROLE_ADMIN') is similar to hasRole(‘ADMIN') because the ‘ROLE_‘ prefix gets added automatically.
    ARCHITECT("ROLE_ARCH"),
    DEVELOPER("ROLE_DEV");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
