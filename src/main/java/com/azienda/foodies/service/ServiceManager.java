package com.azienda.foodies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.foodies.repository.CategoriaRepository;
import com.azienda.foodies.repository.HashtagRepository;
import com.azienda.foodies.repository.PostRepository;
import com.azienda.foodies.repository.UtenteRepository;

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

}
