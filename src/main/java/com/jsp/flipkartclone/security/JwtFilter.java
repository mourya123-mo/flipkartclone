package com.jsp.flipkartclone.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jsp.flipkartclone.entity.AccessToken;
import com.jsp.flipkartclone.repo.AccessTokenRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private AccessTokenRepo accessTokenRepo;

	private JwtService jwtService;

	private CustomUserDetailService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String at = null;
		String rt = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("at"))
				at = cookie.getValue();
			if (cookie.getName().equals("rt"))
				rt = cookie.getValue();
		}
		if (at == null || rt == null)
			throw new RuntimeException("user not loggedin");
		Optional<AccessToken> accessToken = accessTokenRepo.findByTokenAndIsBlocked(at, false);
		if (accessToken == null)
			throw new RuntimeException();
		else {
			log.info(" Authenticating the token");
			String userName = jwtService.extractUserName(at);
			if (userName == null)
				throw new RuntimeException("failed to authenticate");
			UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
					null, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			log.info("authenticated sucessfully");
		}
		filterChain.doFilter(request, response);
	}

}
