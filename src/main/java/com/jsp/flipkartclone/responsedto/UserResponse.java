package com.jsp.flipkartclone.responsedto;

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
public class UserResponse {

	private int userId;
	private String userName;
	private String email;
	private UserRole userRole;
	private boolean isVerified;
	private boolean isDeleted;
	
}
