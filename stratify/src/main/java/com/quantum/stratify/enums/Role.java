package com.quantum.stratify.enums;

public enum Role {
    USER("user"),
    ADMIN("admin"),
    GESTOR("gestor");

    private String role;

    Role(String role){
        this.role = role;
    }
}
