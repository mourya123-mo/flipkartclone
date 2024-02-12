package com.jsp.flipkartclone.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jsp.flipkartclone.entity.AccessToken;
import com.jsp.flipkartclone.entity.RefreshToken;
import com.jsp.flipkartclone.repo.AccessTokenRepo;
import com.jsp.flipkartclone.repo.RefreshTokenRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleJobs {

	private AccessTokenRepo accessTokenRepo;

	private RefreshTokenRepo refreshTokenRepo;

	 @Scheduled(cron = "0 */6 * * *")
	public void removeExpireyRefreshToken() {

		List<RefreshToken> refreshTokenList = refreshTokenRepo.findByExpireationBefore(LocalDateTime.now());
		refreshTokenRepo.deleteAll(refreshTokenList);
	}

	 @Scheduled(cron = "0 */6 * * *")
	public void removeExpireyAccessToken() {

		List<AccessToken> accessTokenList = accessTokenRepo.findByExpireationBefore(LocalDateTime.now());

		accessTokenRepo.deleteAll(accessTokenList);
	}
}
