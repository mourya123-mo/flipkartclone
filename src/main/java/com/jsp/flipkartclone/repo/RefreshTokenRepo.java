package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.RefreshToken;
import java.util.List;


public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
  RefreshToken findByToken(String token);
}
