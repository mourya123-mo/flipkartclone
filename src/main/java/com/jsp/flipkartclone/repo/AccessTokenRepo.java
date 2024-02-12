package com.jsp.flipkartclone.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.AccessToken;


public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

	AccessToken findByToken(String token);
	Optional<AccessToken> findByTokenAndIsBlocked(String token , boolean isBlocked);
	
    List<AccessToken> findByExpireationBefore(LocalDateTime now);
}

