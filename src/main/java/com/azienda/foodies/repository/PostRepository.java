package com.azienda.foodies.repository;

import org.springframework.data.repository.CrudRepository;

import com.azienda.foodies.model.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer>{
    @Override
    public List<Post> findAll();
}
