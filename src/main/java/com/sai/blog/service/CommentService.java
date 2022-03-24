package com.sai.blog.service;

import java.util.List;

import com.sai.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long id, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(long postId, long commentId);
	
	CommentDto updateComment(long postId, long commentId, CommentDto comment);
	
	void deleteCommentById(long postid, long commentid);
	
}
