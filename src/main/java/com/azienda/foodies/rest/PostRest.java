package com.azienda.foodies.rest;

import com.azienda.foodies.model.Post;
import com.azienda.foodies.model.Utente;
import com.azienda.foodies.model.UtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.azienda.foodies.service.ServiceManager;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/posts", produces = "application/json")
@CrossOrigin(origins = "*")
public class PostRest {

	@Autowired
	private ServiceManager serviceManager;

	@GetMapping("/getByUser")
	public ResponseEntity<List<Post>> getByUser(@RequestBody UtenteDTO utenteDTO) {
		try {
			//TODO: check user auth
			Utente utente = null;

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

			// Get posts dell'utente
			List<Post> posts = serviceManager.getPostsByUser(utente.getId());

			// Controllo se la lista è vuota ritorno 404 altrimenti 200 con la lista dei post
			if (posts.size() == 0)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getLastUpdateBetween/{from}/{to}")
	public ResponseEntity<List<Post>> getByUser(@RequestBody UtenteDTO utenteDTO, @PathVariable("from")LocalDateTime from, @PathVariable("to") LocalDateTime to) {
		try {
			//TODO: check user auth
			Utente utente = null;

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

			// Get posts dell'utente
			List<Post> posts = serviceManager.getPostsLastUpdateBetween(from, to);

			// Controllo se la lista è vuota ritorno 404 altrimenti 200 con la lista dei post
			if (posts.size() == 0)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
