package com.jsp.flipkartclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.flipkartclone.requestdto.AuthRequest;
import com.jsp.flipkartclone.requestdto.OtpModel;
import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.AuthResponse;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.util.ResponseStructure;
import com.jsp.flipkartclone.util.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	ResponseEntity<ResponseStructure<UserResponse>> regesterUser(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpModel otpModel);

	public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest,
			HttpServletResponse servletResponse);

	ResponseEntity<ResponseStructure<SimpleResponseStructure>> logout(
			HttpServletResponse httpServletResponse,String accessToken,String refreshToken);
}
