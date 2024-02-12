package com.jsp.flipkartclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flipkartclone.requestdto.AuthRequest;
import com.jsp.flipkartclone.requestdto.OtpModel;
import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.AuthResponse;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.util.ResponseStructure;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthController {

	private AuthService authService;

	@PostMapping(path = "/register")
	public ResponseEntity<ResponseStructure<UserResponse>> regesterUser(@RequestBody UserRequest userRequest) {
		return authService.regesterUser(userRequest);

	}
	@PostMapping(path = "/verify-otp")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestBody OtpModel otpModel){
		return authService.verifyOTP(otpModel);
		
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest,HttpServletResponse servletResponse) {
		return authService.login(authRequest,servletResponse);
	}
}
