package com.azienda.foodies.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.foodies.service.ServiceManager;

@RestController
@RequestMapping(path = "/categorie", produces = "application/json")
@CrossOrigin(origins = "*")
public class CategoriaRest {
	
	@Autowired
	private ServiceManager serviceManager;
	

}
