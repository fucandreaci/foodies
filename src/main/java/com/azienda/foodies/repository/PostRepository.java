package com.azienda.foodies.repository;

import org.springframework.data.repository.CrudRepository;

import com.azienda.foodies.model.Post;

public interface PostRepository extends CrudRepository<Post, Integer>{

}
