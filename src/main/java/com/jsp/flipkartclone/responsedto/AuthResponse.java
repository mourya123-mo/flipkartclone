package com.jsp.flipkartclone.responsedto;

import java.time.LocalDateTime;

import com.jsp.flipkartclone.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
 private int userId;
 private String userName;
 private String role;
 private boolean isAuthenticate;
 private LocalDateTime accessExpiration;
 private LocalDateTime refreshExpiration;
 
}
