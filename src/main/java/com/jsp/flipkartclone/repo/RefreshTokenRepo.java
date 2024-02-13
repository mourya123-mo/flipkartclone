package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.RefreshToken;
import com.jsp.flipkartclone.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
	RefreshToken findByToken(String token);

	List<RefreshToken> findByExpireationBefore(LocalDateTime now);

	List<RefreshToken> findByUserAndIsBlocked(User user, boolean isBlocked);

	List<RefreshToken> findByUserAndIsBlockedAndTokenNot(User user, boolean b, String refreshToken);

}
