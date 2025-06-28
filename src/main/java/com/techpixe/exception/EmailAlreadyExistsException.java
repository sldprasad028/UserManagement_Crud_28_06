package com.techpixe.exception;

public class EmailAlreadyExistsException extends RuntimeException
{
	public EmailAlreadyExistsException(String email)
	{
		super("Email Already Exists ");
	}
}
