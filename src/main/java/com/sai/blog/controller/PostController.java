package com.sai.blog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sai.blog.payload.PostDto;
import com.sai.blog.payload.PostResponse;
import com.sai.blog.service.PostService;
import com.sai.blog.utils.AppConstants;

@RestController
@RequestMapping("/api")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@PostMapping("/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts")
	public PostResponse getAllPosts(@RequestParam(name = AppConstants.DEFAULT_PAGE_NUMBER, defaultValue = "0", required = false) int pageNo,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize,sortBy,sortDir);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
		return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
		return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable Long id) {
		postService.deletePostById(id);
		return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
	}
}
