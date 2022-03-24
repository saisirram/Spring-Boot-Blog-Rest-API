package com.sai.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
