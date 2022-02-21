package com.azienda.foodies.DTO;

import java.time.LocalDateTime;

public class UtenteDTORegistrazione extends UtenteDTOLogin{
	
	private String nome;
	private String cognome;
    private String email;
    private String biografia;
    private String immagineProfilo;
    
	public UtenteDTORegistrazione(String username, String password, String nome, String cognome, String email,
			String biografia, String immagineProfilo) {
		super(username, password);
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
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



	@Override
	public String toString() {
		return "UtenteDTORegistrazione [nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", biografia="
				+ biografia + ", immagineProfilo=" + immagineProfilo + "]";
	}
}
