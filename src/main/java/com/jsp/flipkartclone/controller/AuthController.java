package com.jsp.flipkartclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.util.ResponseStructure;

public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping(path = "/users/regesteruser")
	public ResponseEntity<ResponseStructure<UserResponse>> regesterUser(@RequestBody UserRequest userRequest) {
		return authService.regesterUser(userRequest);

	}
}
