package com.azienda.foodies.service;

import java.util.List;

import com.azienda.foodies.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.foodies.DTO.UtenteDTORegistrazione;
import com.azienda.foodies.exception.ExistingUser;
import com.azienda.foodies.exception.InvalidFields;
import com.azienda.foodies.model.Utente;
import com.azienda.foodies.repository.CategoriaRepository;
import com.azienda.foodies.repository.HashtagRepository;
import com.azienda.foodies.repository.PostRepository;
import com.azienda.foodies.repository.UtenteRepository;
import com.azienda.foodies.utils.CheckOnParameters;

import java.util.List;

import java.time.LocalDateTime;
import java.util.List;

@Service("ServiceManager")
@Transactional
public class ServiceManager {
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private HashtagRepository hashtagRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Utente getUtente(String username, String password){
		List<Utente> u = utenteRepository.findByUsernameAndPassword(username, password);
		
		if(u == null || u.isEmpty())
			return null;
		else
			return u.get(0);
	}
	
	public Utente addUtente(UtenteDTORegistrazione utente) throws Exception{
		String nome = utente.getNome();
		String cognome = utente.getCognome();
		String username = utente.getUsername();
		String password = utente.getPassword();
		String email = utente.getEmail();
		String immagineProfilo = utente.getImmagineProfilo();
		String biografia = utente.getBiografia();
		if(CheckOnParameters.areNotEmpty(
				nome, cognome, username, password, email, immagineProfilo, biografia)) {
			
			if(CheckOnParameters.hasPattern(email, 
						"^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
				if(CheckOnParameters.hasPattern(password, 
						"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
					if(CheckOnParameters.hasPattern(immagineProfilo, 
							"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
						
						if(utenteRepository.findByUsername(username).isEmpty()) {
							if(utenteRepository.findByEmail(email).isEmpty()) {
								
								Utente user = new Utente(nome, cognome, username, email, password, biografia,
										immagineProfilo);
								
								utenteRepository.save(user);
								return user;
								
							}else {
								throw new ExistingUser("Esiste già un utente con tale email.");
							}
						} else {
							throw new ExistingUser("Esiste già un utente con tale username.");
						}
						
					}else {
						throw new InvalidFields("L'url immagine inserita non e' valida.");
					}
				}else {
					throw new InvalidFields("La password deve contenere almeno una lettera"
							+ " maiuscola, una minuscola, un numero, un carattere speciale ed essere"
							+ " lunga almeno 8 caratteri.");
				}
			}else {
				throw new InvalidFields("La email inserita non e' valida.");
			}
		} else {
			throw new InvalidFields("Uno o più campi sono vuoti.");
		}
	}
	
	
	

	public List<Post> getAllPosts () throws Exception {
		return postRepository.findAll();
	}

	public Post inserisciPost (Post post) throws Exception {
		try {
			return postRepository.save(post);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	public List<Post> getPostsByUser (Integer userId) {
		return postRepository.getPostByUtente(userId);
	}

	public List<Post> getPostsLastUpdateBetween(LocalDateTime from, LocalDateTime to) {
		return postRepository.getPostByLastUpdateBetween(from, to);
	}
}
