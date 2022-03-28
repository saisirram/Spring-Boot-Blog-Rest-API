package com.sai.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sai.blog.entity.User;
import com.sai.blog.repository.UserRepository;

public class PasswordEncoderGenerator {

	public static void main(String args[])
	{
		UserRepository userRepo;
		//User user = userRepo.findByUsername("sai").get();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("saiv31"));
		System.out.println(passwordEncoder.matches("saiv31", passwordEncoder.encode("saiv31")));
	}
}
