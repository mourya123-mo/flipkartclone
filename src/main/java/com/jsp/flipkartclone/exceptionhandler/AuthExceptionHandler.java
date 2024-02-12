package com.jsp.flipkartclone.exceptionhandler;

import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.flipkartclone.exception.UserNotLoggedInException;

@RestControllerAdvice
public class AuthExceptionHandler {

	private ResponseEntity<Object> structure(HttpStatus status, String message, Object rootCause) {
		return new ResponseEntity<Object>(Map.of("status", status.value(), "message", message, "rootCause", rootCause),
				status);
	}

//	@ExceptionHandler(ConstraintViolationException.class)
//		public ResponseEntity<Object> handleConstraintViolationException( ConstraintViolationException ex) {
//			return structure(HttpStatus.valueOf(ex.get), ex.getMessage(), ex.getCause());
//
//		}
	
	@ExceptionHandler(UserNotLoggedInException.class)
	public ResponseEntity<Object> handleUserNotLoggedInException(UserNotLoggedInException ex){
		return structure(HttpStatus.valueOf(ex.getStatus()), "message", ex.getRootCause());
	}


}