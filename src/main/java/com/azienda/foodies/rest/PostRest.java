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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/posts", produces = "application/json")
@CrossOrigin(origins = "*")
public class PostRest {

	@Autowired
	private ServiceManager serviceManager;

	@PostMapping(path ="/getAll", consumes = "application/json")
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

	@PostMapping(path = "/insert", consumes = "application/json")
	public ResponseEntity<Post> insertPost(@RequestBody Post post, @RequestBody UtenteDTO utenteDTO) {
		try {
			if (serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword()) == null || post.getTitolo() == null || post.getDescrizione() == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} else {
				serviceManager.inserisciPost(post);

				return new ResponseEntity<>(post, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getLastUpdateBetween/{from}/{to}")
	public ResponseEntity<?> getByLastUpdate(@RequestBody UtenteDTO utenteDTO, @PathVariable("from") LocalDateTime from, @PathVariable("to") LocalDateTime to) {
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

	@PostMapping("/ownPostByLastUpdate/{from}/{to}")
	public ResponseEntity<List<Post>> getOwnPostsByLastUpdate (@RequestBody UtenteDTO utenteDTO, @PathVariable("from") LocalDateTime from, @PathVariable("to") LocalDateTime to) {
		try {
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());

			if (utente == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			List<Post> posts = serviceManager.getPostsLastUpdateBetween(from, to, utente);


			if (!posts.isEmpty()) {
				return new ResponseEntity<>(posts, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("patchPost/{idPost}/{titolo}/{descrizione}")
	public ResponseEntity<?> patchPost (@RequestBody UtenteDTO utenteDTO, @PathVariable("idPost") Integer idPost, @PathVariable("titolo") String titolo, @PathVariable("descrizione") String descrizione) {
		try {
			Optional<Post> opt = serviceManager.getPostById(idPost);
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());
			if (opt.isPresent()) {

				Post post = opt.get();
				if (utente == null || post.getUtente().getId() == utente.getId()) {
					return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
				}

				Post changed = serviceManager.patchPost(post, titolo, descrizione);

				return new ResponseEntity<>(changed, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Post da aggiornare non trovato", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Errore imprevisto nel server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
