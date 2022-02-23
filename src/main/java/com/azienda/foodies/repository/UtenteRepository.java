package com.azienda.foodies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.azienda.foodies.model.Utente;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends CrudRepository<Utente, Integer>{
	public List<Utente> findByUsernameAndPassword(String username, String password);
	public List<Utente> findByUsername(String username);
	public List<Utente> findByEmail(String username);

	@Query("select u from Utente u join u.likes l where l.id = :postId")
	List<Utente> findLikers (@Param("postId") Integer postId);

	@Query("select u from Utente u join u.likes l where l.id = :postId and u.id = :userId")
	Optional<Utente> hasPutLike (@Param("postId") Integer postId, @Param("userId") Integer userId);

	@Query("select u from Utente u join u.unlikes l where l.id = :postId and u.id = :userId")
	Optional<Utente> hasPutUnLike (@Param("postId") Integer postId, @Param("userId") Integer userId);
}
