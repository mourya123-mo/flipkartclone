package com.jsp.flipkartclone.serviceimpl;

import org.springframework.http.ResponseEntity;

import com.jsp.flipkartclone.entity.User;
import com.jsp.flipkartclone.enums.UserRole;
import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.util.ResponseStructure;

public class AuthServiceImpl implements AuthService {

	public <T extends User> UserRequest mapToUser(UserRequest userRequest) {
		return userRequest.builder().email(userRequest.getEmail()).password(userRequest.getPassword())
				.userRole(userRequest.getUserRole()).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> regesterUser(UserRequest userRequest) {
		User user = new User();
		if (user.equals(UserRole.SELLER)) {
			user.setDeleted(false);
			user.setEmailVerified(false);

		}
		return null;
	}

}
