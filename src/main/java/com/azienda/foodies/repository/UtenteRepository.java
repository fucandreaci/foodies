package com.azienda.foodies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.azienda.foodies.model.Utente;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends CrudRepository<Utente, Integer>{
	public List<Utente> findByUsernameAndPassword(String username, String password);
	public List<Utente> findByUsername(String username);
	public List<Utente> findByEmail(String username);
}
