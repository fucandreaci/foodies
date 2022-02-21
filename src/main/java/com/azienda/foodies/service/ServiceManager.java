package com.azienda.foodies.service;

import com.azienda.foodies.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.foodies.repository.CategoriaRepository;
import com.azienda.foodies.repository.HashtagRepository;
import com.azienda.foodies.repository.PostRepository;
import com.azienda.foodies.repository.UtenteRepository;

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

	public List<Post> getPostsByUser (Integer userId) {
		return postRepository.getPostByUtente(userId);
	}

	public List<Post> getPostsLastUpdateBetween(LocalDateTime from, LocalDateTime to) {
		return postRepository.getPostByLastUpdateBetween(from, to);
	}
}
