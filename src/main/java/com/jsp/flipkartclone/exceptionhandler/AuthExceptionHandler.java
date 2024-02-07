package com.jsp.flipkartclone.exceptionhandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler  {
	
private ResponseEntity<Object> structure(HttpStatus status,String message, Object rootCause){
	return new ResponseEntity<Object>(Map.of(
			"status",status.value(),
			"message",message,
			"rootcause",rootCause),status);
	
}
}
