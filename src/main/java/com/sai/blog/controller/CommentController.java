package com.sai.blog.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.sai.blog.payload.CommentDto;
import com.sai.blog.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD REST APIs for Comment Resource")
@RestController
@RequestMapping("/api/")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@ApiOperation(value = " Create Comment REST Api")
	@PostMapping("posts/{postid}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postid") long id,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = " Get all Comments by Post Id REST Api")
	@GetMapping("posts/{postid}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postid") long postid) {
		return commentService.getCommentsByPostId(postid);
	}

	@ApiOperation(value = " Get Comment by id and by Post Id REST Api")
	@GetMapping("posts/{postid}/comments/{commentid}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postid") long postid,
			@PathVariable(name = "commentid") long commentid) {
		return new ResponseEntity<>(commentService.getCommentById(postid, commentid), HttpStatus.OK);
	}
	
	@ApiOperation(value = " Update Comment by Post Id REST Api")
	@PutMapping("posts/{postid}/comments/{commentid}")
	public ResponseEntity<CommentDto> updateCommentById(@PathVariable(name = "postid") long postid,
			@PathVariable(name = "commentid") long commentid, @Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.updateComment(postid, commentid, commentDto), HttpStatus.OK);
	}

	@ApiOperation(value = " Delete Comment by Id and by Post Id REST Api")
	@DeleteMapping("posts/{postid}/comments/{commentid}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postid") long postid,
			@PathVariable(name = "commentid") long commentid) {
		commentService.deleteCommentById(postid, commentid);
		return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
	}
}
