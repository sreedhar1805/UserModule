package com.org.userdetails.controller;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.userdetails.dto.LoginRequest;
import com.org.userdetails.dto.SignupRequest;
import com.org.userdetails.model.City;
import com.org.userdetails.model.Erole;
import com.org.userdetails.model.ForgotPasswordRequest;
import com.org.userdetails.model.Role;
import com.org.userdetails.model.User;
import com.org.userdetails.model.UserStatus;
import com.org.userdetails.model.VerificationToken;
import com.org.userdetails.repository.CityRepository;
import com.org.userdetails.repository.RoleRepository;
import com.org.userdetails.repository.UserRepository;
import com.org.userdetails.repository.VerificationTokenRepository;
import com.org.userdetails.response.JwtResponse;
import com.org.userdetails.response.MessageResponse;
import com.org.userdetails.security.jwt.JwtUtils;
import com.org.userdetails.security.service.UserDetailsImpl;
import com.org.userdetails.service.EmailService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Value("${expiry.time.minutes}")
    private int expiryTimeInMinutes;
	
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  EmailService emailService;
  
  @Autowired
  CityRepository cityRepository;
  
  @Autowired
  VerificationTokenRepository verificationTokenRepository;
  
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		try {

			Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
			if (!optionalUser.isPresent()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
			}

			User user = optionalUser.get();
			if (user.getUserStatus() != UserStatus.ACTIVE) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is not active");
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			logger.info("\u001B[32mUser '{}' authenticated successfully\u001B[0m", userDetails.getUsername());

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(),roles, userDetails.getCityId()));
		} catch (AuthenticationException e) {
			logger.error("\u001B[31mAuthentication failed for user '{}': {}\u001B[0m", loginRequest.getUsername(),
					e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		} catch (Exception e) {
			logger.error("\u001B[31mAn error occurred during authentication: {}\u001B[0m", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      logger.info("\u001B[32mReceived a signup request for username: {}\u001B[0m", signUpRequest.getUsername());
      
      if (userRepository.existsByUsername(signUpRequest.getUsername())) {
          logger.warn("\u001B[33mUsername '{}' is already taken!\u001B[0m", signUpRequest.getUsername());
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
      }

      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
          logger.warn("\u001B[33mEmail '{}' is already in use!\u001B[0m", signUpRequest.getEmail());
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
      }

      // Validate password format
      if (!isPasswordValid(signUpRequest.getPassword())) {
          logger.warn("\u001B[33mPassword does not meet the requirements!\u001B[0m");
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Password does not meet the requirements!"));
      }

      // Create new user's account
      User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
              encoder.encode(signUpRequest.getPassword()));

      Set<String> strRoles = signUpRequest.getRole();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
          Role userRole = roleRepository.findByName(Erole.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      } else {
          strRoles.forEach(role -> {
              switch (role) {
                  case "admin":
                      Role adminRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(adminRole);
                      break;

                  case "user":
                      Role userRole = roleRepository.findByName(Erole.ROLE_USER)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(userRole);
                      break;

                  default:
                      Role otherRole = roleRepository.findByName(Erole.ROLE_USER)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(otherRole);
              }
          });
      }

      user.setRoles(roles);
      user.setUserStatus(UserStatus.ACTIVE);

      Optional<City> cityOptional = cityRepository.findById(signUpRequest.getCityId());
      if(cityOptional.isPresent()) {
          City city = cityOptional.get();
          user.setCity(city);
      }
      
      if (signUpRequest.getPhoneNumber() != null)
          user.setPhoneNumber(signUpRequest.getPhoneNumber());
      userRepository.save(user);
      
      logger.info("\u001B[32mUser registered successfully!\u001B[0m");
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  // Method to validate password format
  private boolean isPasswordValid(String password) {
      // Password must contain at least one uppercase, one lowercase, one digit, one special character, and have a minimum length of 8 characters
      return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
  }
  
  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
      logger.info("\u001B[32mReceived forgot password request for email: {}\u001B[0m", email);
 
      Optional<User> optionalUser = userRepository.findByEmail(email);
      if (!optionalUser.isPresent()) {
          logger.warn("\u001B[33mEmail address {} not found!\u001B[0m", email);
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Email address not found!"));
      }
      User user = optionalUser.get();

      // Generate a password reset token
      String token = UUID.randomUUID().toString();

      // Set expiration time for the token
      VerificationToken resetToken = new VerificationToken(token, user, expiryTimeInMinutes);
      verificationTokenRepository.save(resetToken);

      // Send email with password reset link containing the token
      String resetLink = "http://localhost:8080/api/auth/reset-password-token?token=" + token;
      emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
      logger.info("\u001B[32mPassword reset link sent to email: {}\u001B[0m", user.getEmail());

      // Return success response
      return ResponseEntity.ok(new MessageResponse("Password reset link sent to your email!"));
  }
  
  @PostMapping("/reset-password-token")
  public ResponseEntity<?> resetPasswordWithToken(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
      // Log incoming request
      logger.info("\u001B[32mReceived request to reset password with token: {}\u001B[0m", forgotPasswordRequest.getToken());

      // Validate if newPassword matches confirmPassword
      if (!forgotPasswordRequest.getNewPassword().equals(forgotPasswordRequest.getConfirmPassword())) {
          logger.warn("\u001B[33mPassword reset failed due to mismatched passwords\u001B[0m");
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Passwords do not match!"));
      }

      // Find password reset token
      VerificationToken resetToken = verificationTokenRepository.findByToken(forgotPasswordRequest.getToken());
      if (resetToken == null || resetToken.isExpired()) {
          logger.warn("\u001B[33mPassword reset failed due to invalid or expired token: {}\u001B[0m", forgotPasswordRequest.getToken());
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or expired token!"));
      }

      // Update user's password
      User user = resetToken.getUser();
      user.setPassword(encoder.encode(forgotPasswordRequest.getNewPassword()));
      userRepository.save(user);

      // Log successful password reset
      logger.info("\u001B[32mPassword reset with the token {} successfully\u001B[0m", forgotPasswordRequest.getToken());

      // Invalidate reset token
      verificationTokenRepository.delete(resetToken);

      return ResponseEntity.ok(new MessageResponse("Password reset with the token successfully!"));
  }

}
