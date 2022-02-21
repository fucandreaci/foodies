package com.azienda.foodies.service;

import com.azienda.foodies.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.foodies.repository.CategoriaRepository;
import com.azienda.foodies.repository.HashtagRepository;
import com.azienda.foodies.repository.PostRepository;
import com.azienda.foodies.repository.UtenteRepository;

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
}
