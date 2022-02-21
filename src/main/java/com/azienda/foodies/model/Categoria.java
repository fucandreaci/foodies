package com.azienda.foodies.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nome;
    
    @ManyToMany
    @JoinTable(name="post_categorie",joinColumns =
    @JoinColumn(name="categoria_id"), inverseJoinColumns = 
    @JoinColumn(name="post_id"))
    private List<Post> posts = new ArrayList<Post>();
    
    public Categoria() {}
    
    public Categoria(String nome) {
    	this.nome = nome;
    }

	public Categoria(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nome=" + nome + "]";
	}
    
    
	
}
