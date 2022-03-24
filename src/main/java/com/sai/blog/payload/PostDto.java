package com.sai.blog.payload;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {
	private Long id;
	
	@NotBlank
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@NotBlank
	@Size(min = 10, message = "Post Description should have atleast 10 characters")
	private String description;
	
	@NotBlank
	private String content;
	private Set<CommentDto> comments;
}
