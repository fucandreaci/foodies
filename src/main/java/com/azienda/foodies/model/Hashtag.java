package com.azienda.foodies.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Hashtag {
	@Id
	private String nome;
	
    @ManyToMany(mappedBy = "hashtags")
    private List<Post> posts = new ArrayList<Post>();
	
	public Hashtag() {}
	
	public Hashtag(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	

	@Override
	public String toString() {
		return "Hashtag [nome=" + nome + "]";
	}
}
