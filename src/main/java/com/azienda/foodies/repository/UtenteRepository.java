package com.azienda.foodies.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.azienda.foodies.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Integer>{
	public List<Utente> findByUsernameAndPassword(String username, String password);
	public List<Utente> findByUsername(String username);
	public List<Utente> findByEmail(String username);
}
