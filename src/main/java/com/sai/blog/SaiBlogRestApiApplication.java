package com.sai.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sai.blog.entity.User;
import com.sai.blog.repository.UserRepository;

@SpringBootApplication
public class SaiBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(SaiBlogRestApiApplication.class, args);
		
		UserRepository userRepo = run.getBean(UserRepository.class);
		PasswordEncoder encoder = run.getBean(PasswordEncoder.class);
		User user = userRepo.findByUsername("sai").get();
		
		System.out.println(encoder.encode("sai"));
		System.out.println(encoder.matches("sai", user.getPassword()));
	}

}
