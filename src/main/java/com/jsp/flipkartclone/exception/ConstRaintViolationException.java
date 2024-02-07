package com.jsp.flipkartclone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor

public class ConstRaintViolationException extends RuntimeException {

	private String message;
	private int status;
	private String rootCause;
}
