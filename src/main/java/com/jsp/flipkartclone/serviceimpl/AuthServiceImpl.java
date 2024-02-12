package com.jsp.flipkartclone.serviceimpl;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.flipkartclone.cache.CacheStore;
import com.jsp.flipkartclone.entity.AccessToken;
import com.jsp.flipkartclone.entity.Customer;
import com.jsp.flipkartclone.entity.RefreshToken;
import com.jsp.flipkartclone.entity.Seller;
import com.jsp.flipkartclone.entity.User;
import com.jsp.flipkartclone.repo.AccessTokenRepo;
import com.jsp.flipkartclone.repo.RefreshTokenRepo;
import com.jsp.flipkartclone.repo.UserRepo;
import com.jsp.flipkartclone.requestdto.AuthRequest;
import com.jsp.flipkartclone.requestdto.OtpModel;
import com.jsp.flipkartclone.requestdto.UserRequest;
import com.jsp.flipkartclone.responsedto.AuthResponse;
import com.jsp.flipkartclone.responsedto.UserResponse;
import com.jsp.flipkartclone.security.JwtService;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.util.CookieManager;
import com.jsp.flipkartclone.util.MessageStructure;
import com.jsp.flipkartclone.util.ResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private PasswordEncoder passwordEncoder;
	private UserRepo userRepo;
	private CacheStore<String> otpCacheStore;
	private ResponseStructure<UserResponse> structure;
	private CacheStore<User> userCacheStore;
	private JavaMailSender javaMailSender;
	private AuthenticationManager authenticationManager;
	private CookieManager cookieManager;
	private JwtService jwtService;
	private AccessTokenRepo accessTokenRepo;
	private RefreshTokenRepo refereshTokenRepo;
	private ResponseStructure<AuthResponse> authStructure;
	@Value("${myapp.access.expiry}")
	private int accessExpireyInSeconds;

	@Value("${myapp.refresh.expiry}")
	private int refereshExpireyInSeconds;

	public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepo userRepo, CacheStore<String> otpCacheStore,
			ResponseStructure<UserResponse> structure, CacheStore<User> userCacheStore, JavaMailSender javaMailSender,
			AuthenticationManager authenticationManager, CookieManager cookieManager, JwtService jwtService,
			AccessTokenRepo accessTokenRepo, RefreshTokenRepo refereshTokenRepo,
			ResponseStructure<AuthResponse> authStructure) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
		this.otpCacheStore = otpCacheStore;
		this.structure = structure;
		this.userCacheStore = userCacheStore;
		this.javaMailSender = javaMailSender;
		this.authenticationManager = authenticationManager;
		this.cookieManager = cookieManager;
		this.jwtService = jwtService;
		this.accessTokenRepo = accessTokenRepo;
		this.refereshTokenRepo = refereshTokenRepo;
		this.authStructure = authStructure;
	}

	@SuppressWarnings("unchecked")
	public <T extends User> T mapToUser(UserRequest userRequest) {
		User user = null;
		switch (userRequest.getUserRole()) {
		case CUSTOMER -> {
			user = new Customer();
		}

		case SELLER -> {
			user = new Seller();
		}
		}

		user.setUserName(userRequest.getEmail().split("@")[0]);
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUserRole(userRequest.getUserRole());

		return (T) user;

	}

	public UserResponse mapToUserResponse(User user) {

		return UserResponse.builder().userId(user.getUserId()).userName(user.getUserName()).email(user.getEmail())
				.userRole(user.getUserRole()).isVerified(user.isEmailVerified()).isDeleted(user.isDeleted()).build();

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> regesterUser(UserRequest userRequest) {

		if (userRepo.existsByEmail(userRequest.getEmail()))
			throw new RuntimeException("user already exist with given email");

		String otp = number();

		User user = mapToUser(userRequest);

		userCacheStore.add(userRequest.getEmail(), user);
		otpCacheStore.add(userRequest.getEmail(), otp);
		try {
			sendotpTomail(user, otp);
		} catch (MessagingException e) {

			log.error("The email adress does not Exist");

		}

		return new ResponseEntity<ResponseStructure<UserResponse>>(structure.setData(mapToUserResponse(user))
				.setMessage("please verify through otp sent to mail id").setStatus(HttpStatus.ACCEPTED.value()),
				HttpStatus.ACCEPTED);

	}

	@SuppressWarnings("unused")
	private void grantAccess(HttpServletResponse response, User user) {
		// generating access and refresh tokens
		String accessToken = jwtService.generateAccessToken(user.getUserName());
		String refreshToken = jwtService.generateRefreshToken(user.getUserName());

		// adding access and refresh tokens to response
		response.addCookie(cookieManager.Configure(new Cookie("at", refreshToken), accessExpireyInSeconds));
		response.addCookie(cookieManager.Configure(new Cookie("rt", refreshToken), accessExpireyInSeconds));

		// saving the access and refresh cookies into database
		accessTokenRepo.save(AccessToken.builder().token(refreshToken).isBlocked(false)
				.expireation(LocalDateTime.now().plusSeconds(accessExpireyInSeconds)).build());
		refereshTokenRepo.save(RefreshToken.builder().token(refreshToken).isBlocked(false)
				.expireation(LocalDateTime.now().plusSeconds(accessExpireyInSeconds)).build());

	}

	@SuppressWarnings("unused")
	private void sendotpTomail(User user, String otp) throws MessagingException {
		sendMail(MessageStructure.builder().to(user.getEmail()).subject("complete your Registeration To flipkart")
				.sentDate(new Date(accessExpireyInSeconds))
				.text("hey" + user.getUserName() + "good to see you intrested in flipkart,"
						+ "complete your registeration using the OTP <br>" + "<h1>" + otp + "<h1>"
						+ "note: the optp expires in 1 minute" + "<br> <br>" + "with best regards <br>" + "Flipkart")
				.build());

	}

	@SuppressWarnings("unused")
	private void sendRegisterationmail(User user) throws MessagingException {

		sendMail(MessageStructure.builder().to(user.getEmail()).subject("complete your Registeration To flipkart")
				.sentDate(new Date(accessExpireyInSeconds))
				.text("hey" + user.getUserName() + "registeration sucessful" + "enjoy your shopping experience ")
				.build());

	}

	@Async
	private void sendMail(MessageStructure message) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(message.getTo());
		helper.setSubject(message.getSubject());
		helper.setSentDate(message.getSentDate());
		helper.setText(message.getText(), true);
		javaMailSender.send(mimeMessage);
	}

	private String number() {
		return Integer.toString((int) (Math.random() * (999999 - 100000 + 1)) + 100000);

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpModel otpModel) {
		User user = userCacheStore.get(otpModel.getEmail());
		String otp = otpCacheStore.get(otpModel.getEmail());
		if (otp == null)
			throw new RuntimeException();
		if (user == null)
			throw new RuntimeException();
		if (otp.equals(otpModel.getOtp())) {
			user.setEmailVerified(true);
			userRepo.save(user);
		}
		try {
			sendRegisterationmail(user);
		} catch (MessagingException e) {

			log.error("the email is not verified");
		}
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure.setData(mapToUserResponse(user))
				.setMessage("registeration sucessful").setStatus(HttpStatus.OK.value()), HttpStatus.ACCEPTED);

	}

	public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest,
			HttpServletResponse servletResponse) {
		String username = authRequest.getEmail().split("@")[0];
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
				authRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		if (!authentication.isAuthenticated())
			throw new UsernameNotFoundException("Failed to Authenticate the user");
		else {
			// generating the cookies and authResponse & returning to client
			return userRepo.findByUserName(username).map(user -> {
				grantAccess(servletResponse, user);
				return ResponseEntity.ok(authStructure.setStatus(HttpStatus.OK.value()).setMessage("login sucessful")
						.setData(AuthResponse.builder().userId(user.getUserId()).userName(username)
								.role(user.getUserRole().name()).isAuthenticate(true)
								.accessExpiration(LocalDateTime.now().plusSeconds(accessExpireyInSeconds))
								.refreshExpiration(LocalDateTime.now().plusSeconds(refereshExpireyInSeconds)).build()));

			}).get();

		}
	}
}
