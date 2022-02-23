package com.azienda.foodies.repository;

import com.azienda.foodies.model.Utente;
import org.springframework.data.repository.CrudRepository;

import com.azienda.foodies.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Integer> {
	List<Post> findByVisibile(Boolean visibile);
	Optional<Post> findByIdAndVisibile(Integer id, Boolean visibile);
    public List<Post> findAll();
    List<Post> getPostByUtenteAndVisibile (Integer userid, Boolean visibile);
    List<Post> getPostByLastUpdateBetweenAndVisibile(LocalDateTime from, LocalDateTime to, Boolean visibile);
    List<Post> findByTitoloContainsOrDescrizioneContainsAndVisibileTrue (String titolo, String descrizione);
    List<Post> findByTitoloContainsOrDescrizioneContainsAndUtenteEqualsAndVisibile (String titolo, String descrizione, Utente user, Boolean visibile);
    List<Post> getPostByLastUpdateBetweenAndUtenteEqualsAndVisibile(LocalDateTime from, LocalDateTime to, Utente utente, Boolean visibile);
}
