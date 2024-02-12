package com.jsp.flipkartclone.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SimpleResponseStructure {

	private String message;
	private int status;
}
