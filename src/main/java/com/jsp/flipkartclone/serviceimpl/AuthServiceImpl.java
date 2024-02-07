package com.jsp.flipkartclone.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.flipkartclone.entity.Customer;
import com.jsp.flipkartclone.entity.Seller;
import com.jsp.flipkartclone.entity.User;
import com.jsp.flipkartclone.exception.ConstRaintViolationException;
import com.jsp.flipkartclone.repo.CustomerRepo;
import com.jsp.flipkartclone.repo.SellerRepo;
import com.jsp.flipkartclone.repo.UserRepo;
import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private PasswordEncoder passwordEncoder;
	
	private CustomerRepo customerRepo;

	private UserRepo userRepo;

	private SellerRepo sellerRepo;

	private ResponseStructure<UserResponse> structure;

	@SuppressWarnings("unchecked")
	public <T extends User> T mapToUser(UserRequest userRequest) {
		User user = null;
		switch (userRequest.getUserRole()) {
		case CUSTOMER -> {
			user = new Customer();
		}

		case SELLER -> {
			user = new Seller();
		}
		}

		user.setUserName(userRequest.getEmail().split("@")[0]);
		user.setEmail(userRequest.getEmail());
		//user.setPassword(userRequest.getPassword());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUserRole(userRequest.getUserRole());
		;
		return (T) user;

	}

	public UserResponse mapToUserResponse(User user) {

		return UserResponse.builder().userId(user.getUserId()).userName(user.getUserName()).email(user.getEmail())
				.userRole(user.getUserRole()).isVerified(user.isEmailVerified()).isDeleted(user.isDeleted()).build();

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> regesterUser(UserRequest userRequest) {
//		if(userRepo.existsByEmail(userRequest.getEmail())) throw new ConstRaintViolationException("email already existed",
//				HttpStatus.ALREADY_REPORTED.value(), "");
		User user = userRepo.findByUserName(userRequest.getEmail().split("@")[0]).map(u -> {
			if (u.isEmailVerified())
				throw new ConstRaintViolationException("user already exist with given specifird email",
						HttpStatus.ALREADY_REPORTED.value(), "user name already exist in database");
			else {

			}
			return u;
		}).orElseGet(() -> saveUser(userRequest));

		return new ResponseEntity<ResponseStructure<UserResponse>>(structure.setData(mapToUserResponse(user))
				.setMessage("please check your otp ").setStatus(HttpStatus.ACCEPTED.value()), HttpStatus.ACCEPTED);

	}

	public User saveUser(UserRequest userRequest) {
		User user = null;
		switch (userRequest.getUserRole()) {
		case CUSTOMER -> {
			user = customerRepo.save((Customer) mapToUser(userRequest));
		}
		case SELLER -> {
			user = sellerRepo.save((Seller) mapToUser(userRequest));
		}
		}
		return user;

	}

}
