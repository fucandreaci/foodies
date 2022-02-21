package com.azienda.foodies.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.foodies.DTO.UtenteDTOLogin;
import com.azienda.foodies.DTO.UtenteDTORegistrazione;
import com.azienda.foodies.exception.ExistingUser;
import com.azienda.foodies.exception.InvalidFields;
import com.azienda.foodies.model.Utente;
import com.azienda.foodies.service.ServiceManager;

@RestController
@RequestMapping(path = "/utenti", produces = "application/json")
@CrossOrigin(origins = "*")
public class UtenteRest {
	
	@Autowired
	private ServiceManager serviceManager;
	
	@PostMapping("/postLogin")
	public ResponseEntity<String> login(@RequestBody UtenteDTOLogin user) throws Exception {
		HttpStatus stato;
		try {
			if(serviceManager.getUtente(user.getUsername(), user.getPassword()) != null) {
				stato = HttpStatus.OK;
				return new ResponseEntity<>(null, stato);
			}else {
				stato = HttpStatus.BAD_REQUEST;
				return new ResponseEntity<>("Username o password errati.", stato);
			}
		} catch(Exception e) {
			stato = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>("Errore generico. Conttatare un admin.", stato);
		} 
	}
	
	@PostMapping("/postRegistrazione")
	public ResponseEntity<?> registrazione(@RequestBody UtenteDTORegistrazione user) throws Exception {
		HttpStatus stato;
		try {
			Utente u = serviceManager.addUtente(user);
			stato = HttpStatus.CREATED;
			return new ResponseEntity<>(u, stato);
		} catch(InvalidFields e) {
			stato = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(e.getMessage(), stato);
		} catch(ExistingUser e) {
			stato = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(e.getMessage(), stato);
		} catch(Exception e) {
			stato = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>("Errore nel server", stato);
		}
	}
}
