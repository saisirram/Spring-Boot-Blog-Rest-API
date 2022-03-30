package com.sai.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Comment Model Information")
@Data
public class CommentDto {
	
	@ApiModelProperty(value = "Blog Post comment Id")
	private Long id;
	
	@ApiModelProperty(value = "Blog Post comment message")
	@NotBlank(message = "name should not be empty or blank")
	private String name;
	
	@ApiModelProperty(value = "Blog Post comment email")
	@NotBlank(message = "email must not be empty or blank")
	@Email(message = "email must be valid")
	private String email;
	
	@ApiModelProperty(value = "Blog Post comment body")
	@NotBlank
	@Size(min = 10, message = " comment mus be atleast 10 characters")
	private String body;
	
}
