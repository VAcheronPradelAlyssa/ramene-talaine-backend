package fr.ramenetalaine.backend.dto;

import lombok.Data;

@Data
public class UserSignupDto {
    private String prenom;
    private String nom;
    private String email;
    private String password;
    private String surnom;
    private String ville;
    private String img;
}