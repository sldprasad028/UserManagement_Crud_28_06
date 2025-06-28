package com.techpixe.exception;

public class UserNotFoundException extends RuntimeException
{

	public UserNotFoundException(Long userId) 
	{
		super("User with this "+userId+" is not Found");
	}
	
}
