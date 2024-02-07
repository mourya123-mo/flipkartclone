package com.jsp.flipkartclone.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.flipkartclone.entity.User;
import com.jsp.flipkartclone.exception.userNotFoundByUserName;
import com.jsp.flipkartclone.repo.UserRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	  User  user = userRepo.findByUserName(userName).orElseThrow(()-> new userNotFoundByUserName(userName, 0, userName));
		return new CustomUserDetails(user);
	}

}
