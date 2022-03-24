package com.sai.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	
	@NotBlank(message = "name should not be empty or blank")
	private String name;
	
	@NotBlank(message = "email must not be empty or blank")
	@Email(message = "email must be valid")
	private String email;
	
	@NotBlank
	@Size(min = 10, message = " comment mus be atleast 10 characters")
	private String body;
	
}
