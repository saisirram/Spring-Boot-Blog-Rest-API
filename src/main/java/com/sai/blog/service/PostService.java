package com.sai.blog.service;

import com.sai.blog.payload.PostDto;
import com.sai.blog.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(Long id);
	
	PostDto updatePost(PostDto postDto, Long id);
	
	void deletePostById(Long id);
}
