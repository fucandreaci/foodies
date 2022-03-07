package com.azienda.foodies.repository;

import com.azienda.foodies.model.Utente;

import org.springframework.data.jpa.repository.Query;
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
    List<Post> findByVisibileIsAndTitoloContainsOrDescrizioneContains (boolean b, String titolo, String descrizione);

    //@Query("select u from Utente u join u.likes l where l.id = :postId and l.visibile = 1")

    @Query("select p from Post p where p.utente = :user and p.visibile = true and (p.titolo like %:titolo% or p.descrizione like %:descrizione%)")
    List<Post> findByUtenteEqualsAndVisibileTrueAndTitoloContainsOrDescrizioneContains (Utente user, String titolo, String descrizione);
    List<Post> getPostByLastUpdateBetweenAndUtenteEqualsAndVisibileTrue (LocalDateTime from, LocalDateTime to, Utente utente);
}
