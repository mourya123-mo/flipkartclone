package com.jsp.flipkartclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.util.ResponseStructure;

public interface AuthService {

	ResponseEntity<ResponseStructure<UserResponse>> regesterUser(UserRequest userRequest);

}
