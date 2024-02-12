package com.jsp.flipkartclone.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserNotLoggedInException extends RuntimeException {
	private String message;
	private int status;
	private String rootCause;

}
