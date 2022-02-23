package com.azienda.foodies.service;

import java.util.List;

import com.azienda.foodies.exception.*;
import com.azienda.foodies.model.Post;
import com.azienda.foodies.model.UtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.foodies.DTO.UtenteDTORegistrazione;
import com.azienda.foodies.model.Utente;
import com.azienda.foodies.repository.CategoriaRepository;
import com.azienda.foodies.repository.HashtagRepository;
import com.azienda.foodies.repository.PostRepository;
import com.azienda.foodies.repository.UtenteRepository;
import com.azienda.foodies.utils.CheckOnParameters;

import java.util.List;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

	public List<Post> getPostContainsTitoloOrTesto (String titolo, String testo) {
		return postRepository.findAllByTitoloContainsOrDescrizioneContains(titolo, testo);
	}
	public List<Post> getPostContainsTitoloOrTestoProprietario (String titolo, String testo, Utente u) {
		return postRepository.findAllByTitoloContainsOrDescrizioneContainsAndUtenteEquals(titolo, testo, u);
	}

	public List<Utente> findLikers (Integer postId) {
		return utenteRepository.findLikers(postId);
	}

	public Post getPostById (Integer id) {
		Optional<Post> post = postRepository.findById(id);
		return post.isPresent() ? post.get() : null;
	}

	public void addLike (Utente u, Integer postId) throws NotFoundException, AlreadyPutLikeException, AutolikeException {
		Post post = getPostById(postId);
		if (post == null)
			throw new NotFoundException("Post non trovato");

		if (utenteRepository.hasPutLike(postId, u.getId()).isPresent())
			throw new AlreadyPutLikeException("Hai già messo like a questo post");

		if (post.getUtente().getId() == u.getId()) throw new AutolikeException("Non puoi mettere like al tuo post");

		u.getLikes().add(post);
		u.getUnlikes().remove(post);

		post.getLikes().add(u);
		post.getUnlikes().remove(u);

		postRepository.save(post);
		utenteRepository.save(u);
	}

	public void addUnLike (Utente u, Integer postId) throws NotFoundException, AlreadyPutLikeException, AutolikeException {
		Post post = getPostById(postId);
		if (post == null)
			throw new NotFoundException("Post non trovato");

		if (utenteRepository.hasPutUnLike(postId, u.getId()).isPresent())
			throw new AlreadyPutLikeException("Hai già messo unlike a questo post");

		if (post.getUtente().getId() == u.getId()) throw new AutolikeException("Non puoi mettere unlike al tuo post");

		u.getLikes().remove(post);
		u.getUnlikes().add(post);

		post.getLikes().remove(u);
		post.getUnlikes().add(u);

		postRepository.save(post);
		utenteRepository.save(u);
	}

	public List<Post> getPostsLastUpdateBetween(LocalDateTime from, LocalDateTime to, Utente utente) {
		return postRepository.getPostByLastUpdateBetweenAndUtenteEquals(from, to, utente);
	}

	public Post patchPost (Post post, String titolo, String descrizione) throws Exception {
		try {
			if (titolo != null) post.setTitolo(titolo);
			if (descrizione != null) post.setDescrizione(descrizione);

			post.setLastUpdate(LocalDateTime.now());

			return postRepository.save(post);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
