package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.AccessToken;
import java.util.List;


public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

	AccessToken findByToken(String token);
}
