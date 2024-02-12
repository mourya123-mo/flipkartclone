package com.jsp.flipkartclone.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ConstRaintViolationException extends RuntimeException {

	private String message;
	private int status;
	private String rootCause;
}
