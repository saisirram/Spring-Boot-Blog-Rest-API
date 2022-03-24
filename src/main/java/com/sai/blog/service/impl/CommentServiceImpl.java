package com.sai.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sai.blog.entity.Comment;
import com.sai.blog.entity.Post;
import com.sai.blog.exception.BlogAPIException;
import com.sai.blog.exception.ResourceNotFoundException;
import com.sai.blog.payload.CommentDto;
import com.sai.blog.repository.CommentRepository;
import com.sai.blog.repository.PostRepository;
import com.sai.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper modelMapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);

		// retrieve the post based on its id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		// set post to comment Entity
		comment.setPost(post);

		// save to DB
		Comment savedComment = commentRepository.save(comment);

		return mapToDto(savedComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// retrieve comments by PostId
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {

		// retrieve the post based on its id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if (!comment.getPost().getId().equals(postId)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "requested comment is not belongs to this post");
		}
		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if (!comment.getPost().getId().equals(postId)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "requested comment is not belongs to this post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		Comment savedComment = commentRepository.save(comment);

		return mapToDto(savedComment);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if (!comment.getPost().getId().equals(postId)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "requested comment is not belongs to this post");
		}

		commentRepository.deleteById(commentId);
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		
	//	CommentDto commentDto = new CommentDto();
	//	commentDto.setId(comment.getId());
	//	commentDto.setName(comment.getName());
	//	commentDto.setEmail(comment.getEmail());
	//	commentDto.setBody(comment.getBody());

		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
	//	Convential way of Conversion
	//	Comment comment = new Comment();
	//	comment.setId(commentDto.getId());
	//	comment.setName(commentDto.getName());
	//	comment.setEmail(commentDto.getEmail());
	//	comment.setBody(commentDto.getBody());

		return comment;
	}
}
