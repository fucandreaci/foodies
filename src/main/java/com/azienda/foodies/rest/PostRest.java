package com.azienda.foodies.rest;

import com.azienda.foodies.DTO.DateDTO;
import com.azienda.foodies.DTO.PostDTO;
import com.azienda.foodies.DTO.UtenteDTOLogin;
import com.azienda.foodies.exception.AlreadyPutLikeException;
import com.azienda.foodies.exception.AutolikeException;
import com.azienda.foodies.exception.InvalidFields;
import com.azienda.foodies.exception.NotFoundException;
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
			List<Post> posts = serviceManager.getPostsByUser(utente);

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
				post1.setVisibile(true);

				//TODO: Creazione tabella hashatag

				return new ResponseEntity<>(serviceManager.inserisciPost(post1), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getLastUpdateBetween")
	public ResponseEntity<?> getByLastUpdate(@RequestBody DateDTO dateDTO) {
		try {
			Utente utente = serviceManager.getUtente(dateDTO.getUsername(), dateDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

			// Get posts dell'utente
			List<Post> posts = serviceManager.getPostsLastUpdateBetween(dateDTO.getFrom(), dateDTO.getTo());

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

	@GetMapping("/ownPostByLastUpdate")
	public ResponseEntity<List<Post>> getOwnPostsByLastUpdate (@RequestBody DateDTO dateDTO) {
		try {
			Utente utente = serviceManager.getUtente(dateDTO.getUsername(), dateDTO.getPassword());

			if (utente == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			List<Post> posts = serviceManager.getPostsLastUpdateBetween(dateDTO.getFrom(), dateDTO.getTo(), utente);


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

	@PostMapping("/addLike/{postId}")
	public ResponseEntity<?> addLike (@RequestBody UtenteDTOLogin utenteDTO, @PathVariable("postId") Integer postId) {
		try {
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

			serviceManager.addLike(utente, postId);
			return ResponseEntity.ok("Like aggiunto");
		} catch (NotFoundException | AlreadyPutLikeException | AutolikeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addUnLike/{postId}")
	public ResponseEntity<?> addUnLike (@RequestBody UtenteDTOLogin utenteDTO, @PathVariable("postId") Integer postId) {
		try {
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());

			// Credenziali utente non valide
			if (utente == null)
				return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);

			serviceManager.addUnLike(utente, postId);
			return ResponseEntity.ok("Unlike aggiunto");
		} catch (NotFoundException | AlreadyPutLikeException | AutolikeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("patchPost/{idPost}")
	public ResponseEntity<?> patchPost (@RequestBody PostDTO postDTO, @PathVariable("idPost") Integer idPost) {
		try {
			Post post = serviceManager.getPostById(idPost);
			Utente utente = serviceManager.getUtente(postDTO.getUsername(), postDTO.getPassword());
			if (post == null || utente == null || post.getUtente().getId() != utente.getId()) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			Post changed = serviceManager.patchPost(post, postDTO);

			return new ResponseEntity<>(changed, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Errore imprevisto nel server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("deletePost/{idPost}")
	public ResponseEntity<?> deletePost (@RequestBody UtenteDTOLogin utenteDTO, @PathVariable("idPost") Integer idPost){
		try {
			Utente utente = serviceManager.getUtente(utenteDTO.getUsername(), utenteDTO.getPassword());
			if(utente == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			
			serviceManager.deletePost(utente, idPost);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			
		} catch(InvalidFields | NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Errore imprevisto nel server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
