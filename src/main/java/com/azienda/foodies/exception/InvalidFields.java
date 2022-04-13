package com.azienda.foodies.exception;

public class InvalidFields extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidFields(String messaggio) {
		super(messaggio);
	}
}
