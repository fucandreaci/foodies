package com.azienda.foodies.exception;

public class ExistingUser extends Exception{
	private static final long serialVersionUID = 1L;

	public ExistingUser(String messaggio) {
		super(messaggio);
	}
}
