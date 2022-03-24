package com.sai.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sai.blog.entity.Post;
import com.sai.blog.exception.ResourceNotFoundException;
import com.sai.blog.payload.PostDto;
import com.sai.blog.payload.PostResponse;
import com.sai.blog.repository.PostRepository;
import com.sai.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper modelMapper;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		super();
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		// convert DTO to Entity
		Post post = mapToEntity(postDto);

		Post newPost = postRepository.save(post);

		// convert Entity to DTO
		PostDto postResponse = mapToDto(newPost);

		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		//Checking for sort direction and Creating sort object
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		// Creating a Pageable instance for fetching the posts based on the pageNo and
		// PageSize
		Pageable pagedPosts = PageRequest.of(pageNo, pageSize, sort);

		// retrieving Data from database based on pageNo and pageSize
		Page<Post> allPosts = postRepository.findAll(pagedPosts);

		// getting content of Page obj to List
		List<Post> posts = allPosts.getContent();

		// Mapping Post to PostDto
		List<PostDto> content = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponse response = new PostResponse();
		response.setContent(content);
		response.setPageNo(allPosts.getNumber());
		response.setPageSize(allPosts.getSize());
		response.setTotalElements(allPosts.getTotalElements());
		response.setTotalPages(allPosts.getTotalPages());
		response.setLast(allPosts.isLast());

		return response;
	}

	@Override
	public PostDto getPostById(Long id) {
		Post findPostById = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

		return mapToDto(findPostById);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		// get the post from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

		// Update the Post with updated values fro DTO
		Post updatedPost = mapToEntity(postDto);
		updatedPost.setId(id);

		// Save the Post into Database
		Post savedPost = postRepository.save(updatedPost);
		return mapToDto(savedPost);
	}

	@Override
	public void deletePostById(Long id) {
		// get the post from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

		postRepository.delete(post);
	}

	// Convert Post(Entity) to PostDto(DTO)
	private PostDto mapToDto(Post post) {
		PostDto postDto = modelMapper.map(post, PostDto.class);
		
		//Conventional Way of Conversion
		//	postDto postDto = new PostDto();
		//	postDto.setId(post.getId());
		//	postDto.setTitle(post.getTitle());
		//	postDto.setDescription(post.getDescription());
		//	postDto.setContent(post.getContent());
		return postDto;
	}

	// Convert PostDto(DTO) to Post(Entity)
	private Post mapToEntity(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		
		//Conventional Way of Conversion
		//	Post post = new Post();
		//	post.setTitle(postDto.getTitle());
		//	post.setDescription(postDto.getDescription());
		//	post.setContent(postDto.getContent());

		return post;

	}
}
