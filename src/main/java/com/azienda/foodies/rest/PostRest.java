package com.azienda.foodies.rest;

import com.azienda.foodies.model.Post;
import com.azienda.foodies.model.UtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.azienda.foodies.service.ServiceManager;

import java.util.List;

@RestController
@RequestMapping(path = "/posts", produces = "application/json")
@CrossOrigin(origins = "*")
public class PostRest {

	@Autowired
	private ServiceManager serviceManager;

	@PostMapping(path ="", consumes = "application/json")
	public ResponseEntity<List<Post>> getAll(@RequestBody UtenteDTO utenteDTO) {
		try {
			// TODO: controllo delle credenziali
			if (utenteDTO.getUsername() != null) {
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

	@PostMapping(path = "", consumes = "application/json")
	public ResponseEntity<Post> insertProdotto(@RequestBody Post post, @RequestBody UtenteDTO utenteDTO) {
		try {
			// TODO: controllo delle credenziali nell'if
			if (post.getId() != 0) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			Post post1 = serviceManager.inserisciPost(post);

			return new ResponseEntity<>(post, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
