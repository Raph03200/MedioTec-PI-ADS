package com.agendasenac.modells;

public class LoginRequest {
    private String userEmail;
    private String userSenha;

    
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSenha() {
        return userSenha;
    }

    public void setUserSenha(String userSenha) {
        this.userSenha = userSenha;
    }
}

