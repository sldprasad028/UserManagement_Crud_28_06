package com.techpixe.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler 
{
//	@ExceptionHandler(ResponseStatusException.class)
//	public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
//		Map<String, Object> response = new HashMap<>();
//		// Get the status and message from the exception object
//		HttpStatus status = (HttpStatus) ex.getStatusCode();
//		String message = ex.getReason();
//
//		response.put("status", status.value());
//		response.put("error", status.getReasonPhrase());
//		response.put("message", message);
//
//		return new ResponseEntity<>(response, status);
//	}

	
//	@ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex)
//	{
//        Map<String, Object> error = new HashMap<>();
//        error.put("status", HttpStatus.NOT_FOUND.value());
//        error.put("error", "Not Found");
//        error.put("message", ex.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
	
	
	
	// ApiResponse<Object> :: For Global Exception handler.
	//  Allows flexibility to return different types of error payloads (strings, maps, lists, etc.).
    // You often return a list of errors (e.g., validation errors),  Or a Map with error details, Or a simple message string.And Object provides flexibility for all these shapes.
	

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex)
	{
	    List<Map<String, String>> errorList = new ArrayList<>();

	    for (FieldError error : ex.getBindingResult().getFieldErrors())
	    {
	        Map<String, String> err = new HashMap<>();
	        err.put("field", error.getField());
	        err.put("message", error.getDefaultMessage());
	        errorList.add(err);
	    }

	    return new ResponseEntity<>(ApiResponse.failure(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errorList), HttpStatus.BAD_REQUEST);
	}


	
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) 
	{
        return new ResponseEntity<>(ApiResponse.failure(ex.getStatusCode().value(), ex.getReason()),ex.getStatusCode());
    }

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFound(UserNotFoundException ex)
	{
        return new ResponseEntity<>(ApiResponse.failure(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExists(EmailAlreadyExistsException ex)
	{
	    return new ResponseEntity<>(ApiResponse.failure(HttpStatus.CONFLICT.value(), ex.getMessage()),HttpStatus.CONFLICT);
	}

	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ApiResponse.failure(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
	
	@ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(NoHandlerFoundException ex) 
	{
        return new ResponseEntity<>(ApiResponse.failure(HttpStatus.NOT_FOUND.value(), "URL does not exist"), HttpStatus.NOT_FOUND);
    }
	
	
	//------------------------------------------General-----------------------------------------------
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) 
	{
        return new ResponseEntity<>(ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	

}
