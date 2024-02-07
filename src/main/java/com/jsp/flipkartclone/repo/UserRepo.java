package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);
	Optional<User> findByUserName(String userName);
	
}
