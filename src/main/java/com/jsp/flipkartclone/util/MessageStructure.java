package com.jsp.flipkartclone.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageStructure {

	private String to;
	private String subject;
	private java.util.Date sentDate;
	private String text;

}
