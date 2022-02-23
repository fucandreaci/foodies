package com.azienda.foodies.model;
/*
 * File: Utente
 * Project: Foodies
 * File Created: 21/02/22 - 09:35
 * Author: Andrea Fucci (fucciandrea01@gmail.com)
 * Copyright © 2022-2022 Andrea Fucci
 */

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String immagineProfilo;
    
    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Post> posts = new ArrayList<Post>();
    
    @ManyToMany
    @JoinTable(name="likes",joinColumns =
    @JoinColumn(name="utente_id"), inverseJoinColumns = 
    @JoinColumn(name="post_id"))
    @JsonIgnore
    private List<Post> likes = new ArrayList<Post>();
    
    @ManyToMany
    @JoinTable(name="unlikes",joinColumns =
    @JoinColumn(name="utente_id"), inverseJoinColumns = 
    @JoinColumn(name="post_id"))
    @JsonIgnore
    private List<Post> unlikes = new ArrayList<Post>();

    public Utente() {
    }

	public Utente(int id, String nome, String cognome, String username, String email, String password, String biografia, String immagineProfilo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.biografia = biografia;
        this.immagineProfilo = immagineProfilo;
    }

    public Utente(String nome, String cognome, String username, String email, String password, String biografia, String immagineProfilo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.biografia = biografia;
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

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Post> getLikes() {
		return likes;
	}

	public void setLikes(List<Post> likes) {
		this.likes = likes;
	}

	public List<Post> getUnlikes() {
		return unlikes;
	}

	public void setUnlikes(List<Post> unlikes) {
		this.unlikes = unlikes;
	}
	
	

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", username=" + username + ", email="
				+ email + ", password=" + password + ", biografia=" + biografia
				+ ", immagineProfilo=" + immagineProfilo + ", posts=" + posts + ", likes=" + likes + ", unlikes="
				+ unlikes + "]";
	}
}
