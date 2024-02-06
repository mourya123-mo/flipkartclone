package com.jsp.flipkartclone.requestdto;

import com.jsp.flipkartclone.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

	
	private String email;
	private String password;
	private UserRole userRole;
	
}
