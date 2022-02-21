package com.azienda.foodies.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String descrizione;
    private LocalDateTime dataPubblicazione;
    private String immagine;
    
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;
    
    @ManyToMany(mappedBy = "likes")
    private List<Utente> likes = new ArrayList<Utente>();
    
    @ManyToMany(mappedBy = "unlikes")
    private List<Utente> unlikes = new ArrayList<Utente>();
    
    @ManyToMany(mappedBy = "posts")
    private List<Categoria> categorie = new ArrayList<Categoria>();
    
    @ManyToMany
    @JoinTable(name="post_hashtag",joinColumns =
    @JoinColumn(name="hashtag_id"), inverseJoinColumns = 
    @JoinColumn(name="post_id"))
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();
	
	public Post() {}

	public Post(String descrizione, LocalDateTime dataPubblicazione, String immagine) {
		super();
		this.descrizione = descrizione;
		this.dataPubblicazione = dataPubblicazione;
		this.immagine = immagine;
	}

	public Post(int id, String descrizione, LocalDateTime dataPubblicazione, String immagine) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataPubblicazione = dataPubblicazione;
		this.immagine = immagine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(LocalDateTime dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public List<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(List<Categoria> categorie) {
		this.categorie = categorie;
	}

	public List<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public List<Utente> getLikes() {
		return likes;
	}

	public void setLikes(List<Utente> likes) {
		this.likes = likes;
	}

	public List<Utente> getUnlikes() {
		return unlikes;
	}

	public void setUnlikes(List<Utente> unlikes) {
		this.unlikes = unlikes;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", descrizione=" + descrizione + ", dataPubblicazione=" + dataPubblicazione
				+ ", immagine=" + immagine + ", categorie=" + categorie
				+ ", hashtags=" + hashtags + "]";
	}
	
}
