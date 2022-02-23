package com.azienda.foodies.repository;

import com.azienda.foodies.model.Utente;

import org.springframework.data.repository.CrudRepository;
import com.azienda.foodies.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Integer> {
	List<Post> findByVisibileTrue();
	Optional<Post> findByIdAndVisibileTrue(Integer id);
    public List<Post> findAll();
    List<Post> getPostByUtenteAndVisibileTrue (Utente utente);
    List<Post> getPostByLastUpdateBetweenAndVisibileTrue (LocalDateTime from, LocalDateTime to);
    List<Post> findByTitoloContainsOrDescrizioneContainsAndVisibileTrue (String titolo, String descrizione);
    List<Post> findByTitoloContainsOrDescrizioneContainsAndUtenteEqualsAndVisibileTrue (String titolo, String descrizione, Utente user);
    List<Post> getPostByLastUpdateBetweenAndUtenteEqualsAndVisibileTrue (LocalDateTime from, LocalDateTime to, Utente utente);
}
