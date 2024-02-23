package com.jsp.flipkartclone.responsedto;

import com.jsp.flipkartclone.enums.Priority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponse {

	private int contactId;
	private String name;
	private long contactNo;
	private Priority priority;
}
