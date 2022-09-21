package com.example.communicatorproject.repository;

import com.example.communicatorproject.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "post", path = "post")
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select post from Post post where post.replyToId is null")
    List<Post> findAllPosts();
}
