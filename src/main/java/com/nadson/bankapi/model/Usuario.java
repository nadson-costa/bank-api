package com.nadson.bankapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Usuario(){

    }

    @PrePersist
    protected void onCrete(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void SetNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void SetEmail(String email){
        this.email = email;
    }

    public String getSenhaHash(){
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash){
        this.senhaHash = senhaHash;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

}
