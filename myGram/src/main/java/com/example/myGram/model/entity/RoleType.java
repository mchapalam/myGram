package com.example.myGram.model.entity;

public enum RoleType {
    ROLE_ADMIN ("ROLE_ADMIN"), ROLE_USER ("ROLE_USER");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
