package com.jsp.flipkartclone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor

public class userNotFoundByUserName extends RuntimeException {
  private String message;
  private int Status;
  private String rootCause;
}
