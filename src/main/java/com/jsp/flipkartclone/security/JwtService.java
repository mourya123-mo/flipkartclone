package com.jsp.flipkartclone.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${myapp.secret}")
	private String secret;

	@Value("${myapp.access.expiry}")
	private Long accessExpireationInSeconds;

	@Value("${myapp.refresh.expiry}")
	private Long refreshExpireationInSecons;

	public String generateAccessToken(String userName) {
		return generateJWT(new HashMap<String, Object>(), userName, accessExpireationInSeconds * 1000l);
	}

	public String generateRefreshToken(String userName) {
		return generateJWT(new HashMap<String, Object>(), userName, refreshExpireationInSecons * 1000l);
	}

	@SuppressWarnings("unused")
	private String generateJWT(Map<String, Object> claims, String userName, long expiry) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiry))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS512)// signing jwt with key
				.compact();

	}

	@SuppressWarnings("unused")
	private Key getSignatureKey() {
		byte[] secretByte = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretByte);
	}

	private Claims jwtParser(String token) {
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build();
		return jwtParser.parseClaimsJws(token).getBody();

	}

	public String extractUserName(String token) {
		return jwtParser(token).getSubject();

	}
}
