package com.azienda.foodies.rest;

import com.azienda.foodies.DTO.PostDTO;
import com.azienda.foodies.DTO.UtenteDTOLogin;
import com.azienda.foodies.model.Post;

import com.azienda.foodies.model.Utente;
import com.azienda.foodies.model.UtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.azienda.foodies.service.ServiceManager;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/posts", produces = "application/json")
@CrossOrigin(origins = "*")
public class PostRest {

	@Autowired
	private ServiceManager serviceManager;

	@GetMapping(path ="/getAll", consumes = "application/json")
	public ResponseEntity<List<Post>> getAll(@RequestBody UtenteDTO utenteDTO) {
		try {
			if (serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword()) != null) {
				List<Post> posts = serviceManager.getAllPosts();
				if (posts.isEmpty()) {
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(posts, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByUser")
	public ResponseEntity<?> getByUser(@RequestBody UtenteDTO utenteDTO) {
		try {
			//TODO: check user auth
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

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

	@PostMapping("/insert")
	public ResponseEntity<Post> insertPost(@RequestBody PostDTO post) {
		try {
			Utente user = serviceManager.getUtente(post.getUsername(), post.getPassword());
			if (user == null || post.getTitolo() == null || post.getDescrizione() == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} else {
				Post post1 = new Post();
				post1.setTitolo(post.getTitolo());
				post1.setDescrizione(post.getDescrizione());
				post1.setDataPubblicazione(LocalDateTime.now());
				post1.setImmagine(post.getImmagine());
				post1.setUtente(user);

				//TODO: Creazione tabella hashatag

				return new ResponseEntity<>(serviceManager.inserisciPost(post1), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getLastUpdateBetween/{from}/{to}")
	public ResponseEntity<?> getByLastUpdate(@RequestBody UtenteDTOLogin utenteDTO, @PathVariable("from") LocalDateTime from, @PathVariable("to") LocalDateTime to) {
		try {
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

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

	@GetMapping("/getTitoloOrDescrizioneContains")
	public ResponseEntity<?> getTitoloOrDescrizioneContains (@RequestBody PostDTO postDTO) {
		try {
			Utente utente = serviceManager.getUtente(postDTO.getUsername(), postDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

			List<Post> posts = serviceManager.getPostContainsTitoloOrTesto(postDTO.getTitolo(), postDTO.getDescrizione());

			if (posts.size() == 0)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getTitoloOrDescrizioneContainsProprietario")
	public ResponseEntity<?> getTitoloOrDescrizioneContainsProprietario (@RequestBody PostDTO postDTO) {
		try {
			Utente utente = serviceManager.getUtente(postDTO.getUsername(), postDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

			List<Post> posts = serviceManager.getPostContainsTitoloOrTestoProprietario(postDTO.getTitolo(), postDTO.getDescrizione(), utente);

			if (posts.size() == 0)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
