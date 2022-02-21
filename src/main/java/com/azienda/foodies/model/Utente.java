package com.azienda.foodies.model;/*
 * File: Utente
 * Project: Foodies
 * File Created: 21/02/22 - 09:35
 * Author: Andrea Fucci (fucciandrea01@gmail.com)
 * Copyright © 2022-2022 Andrea Fucci
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;
    private String biografia;
    private LocalDateTime dataFineBan;
    private String immagineProfilo;

    public Utente() {
    }

    public Utente(int id, String nome, String cognome, String username, String email, String password, String biografia, LocalDateTime dataFineBan, String immagineProfilo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.biografia = biografia;
        this.dataFineBan = dataFineBan;
        this.immagineProfilo = immagineProfilo;
    }

    public Utente(String nome, String cognome, String username, String email, String password, String biografia, LocalDateTime dataFineBan, String immagineProfilo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.biografia = biografia;
        this.dataFineBan = dataFineBan;
        this.immagineProfilo = immagineProfilo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public LocalDateTime getDataFineBan() {
        return dataFineBan;
    }

    public void setDataFineBan(LocalDateTime dataFineBan) {
        this.dataFineBan = dataFineBan;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }
}
