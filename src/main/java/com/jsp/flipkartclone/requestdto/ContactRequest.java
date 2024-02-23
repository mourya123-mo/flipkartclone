package com.jsp.flipkartclone.requestdto;

import com.jsp.flipkartclone.enums.Priority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactRequest {

	private String name;
	private long contactNo;
	private Priority priority;

}
