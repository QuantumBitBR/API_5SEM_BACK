package com.quantum.stratify.enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    OPERADOR("OPERADOR"),
    GESTOR("GESTOR");

    private String role;

    Role(String role){
        this.role = role;
    }
}
