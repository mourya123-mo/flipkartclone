package com.jsp.flipkartclone.util;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleResponseStructure {

	private String message;
	private int status;
	
	public String getMessage() {
		return message;
	}
	public SimpleResponseStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	public int getStatus() {
		return status;
	}
	public SimpleResponseStructure setStatus(int status) {
		this.status = status;
		return this;
	}
	
}
