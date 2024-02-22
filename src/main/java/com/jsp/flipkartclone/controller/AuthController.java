package com.jsp.flipkartclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flipkartclone.requestdto.AuthRequest;
import com.jsp.flipkartclone.requestdto.OtpModel;
import com.jsp.flipkartclone.requestdto.StoreRequest;
import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.AuthResponse;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.util.ResponseStructure;
import com.jsp.flipkartclone.util.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
public class AuthController {

	private AuthService authService;

	@PostMapping(path = "/register")
	public ResponseEntity<ResponseStructure<UserResponse>> regesterUser(@RequestBody  UserRequest userRequest) {
		System.err.println(userRequest.getUserRole());
		return authService.regesterUser(userRequest);

	}

	@PostMapping(path = "/verify-otp")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestBody OtpModel otpModel) {
		return authService.verifyOTP(otpModel);

	}

	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(@RequestBody AuthRequest authRequest,
			HttpServletResponse servletResponse) {
		return authService.login(authRequest, servletResponse);
	}

	@PostMapping("/logout")
//	@PreAuthorize(value = "hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	public ResponseEntity<SimpleResponseStructure> logout(HttpServletResponse httpServletResponse,
			@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken) {
		return authService.logout(httpServletResponse, accessToken, refreshToken);
	}

//	@PreAuthorize(value = "hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PostMapping("/revoke-all")
	public ResponseEntity<SimpleResponseStructure> revokeAll() {

		return authService.revokeAll();
	}

//	@PreAuthorize(value = "hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PostMapping("/revoke-other-devices")
	public ResponseEntity<SimpleResponseStructure> revokeOtherDevices(
			@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken) {
		return authService.revokeOtherDevices(accessToken, refreshToken);

	}
	@PostMapping("/refresh-login")
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken, HttpServletResponse httpServletResponse){
				return authService.refreshLogin(accessToken,refreshToken,httpServletResponse) ;
		
	}
	
}
