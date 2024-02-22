package com.jsp.flipkartclone.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.AccessToken;
import com.jsp.flipkartclone.entity.User;

public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

	AccessToken findByToken(String token);

	List<AccessToken> findByTokenAndIsBlocked(String token, boolean isBlocked);

	List<AccessToken> findByUserAndIsBlocked(User user, boolean isBlocked);

	List<AccessToken> findByExpireationBefore(LocalDateTime now);

	List<AccessToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String accessToken);
}
