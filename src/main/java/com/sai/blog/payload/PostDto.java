package com.sai.blog.payload;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Post Model Information")
@Data
public class PostDto {
	
	@ApiModelProperty(value = "Blog Post Id")
	private Long id;
	
	@ApiModelProperty(value = "Blog Post title")
	@NotBlank
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@ApiModelProperty(value = "Blog Post description")
	@NotBlank
	@Size(min = 10, message = "Post Description should have atleast 10 characters")
	private String description;
	
	@ApiModelProperty(value = "Blog Post content")
	@NotBlank
	private String content;
	
	@ApiModelProperty(value = "Blog Post comments")
	private Set<CommentDto> comments;
}
