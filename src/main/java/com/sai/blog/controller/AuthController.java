package com.sai.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.blog.entity.Role;
import com.sai.blog.entity.User;
import com.sai.blog.payload.JWTAuthResponse;
import com.sai.blog.payload.LoginDto;
import com.sai.blog.payload.SignUpDto;
import com.sai.blog.repository.RoleRepository;
import com.sai.blog.repository.UserRepository;
import com.sai.blog.security.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value ="Auth Controller exposes Signin and Signup REST APIs") //Swagger Annotation

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@ApiOperation(value = " Rest API to Signin user to Blog App") //Swagger Annotation
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get token from token provider class
		String token = tokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTAuthResponse(token));
	}

	@ApiOperation(value = " Rest API to Register a user to Blog App")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
		// Check for username exist in DB
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
		}

		// Check for email existance in DB
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email is already Registered", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
		user.setUsername(signUpDto.getUsername());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		Role roles = roleRepository.findByName("ROLE_USER").get();

		user.setRoles(Collections.singleton(roles));

		userRepository.save(user);
		return new ResponseEntity<>("User registered Successfully!! ", HttpStatus.OK);
	}

}
