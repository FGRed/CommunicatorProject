package com.example.communicatorproject.controller;

import com.example.communicatorproject.init.post.PostService;
import com.example.communicatorproject.model.formdata.PostcardViewData;
import com.example.communicatorproject.model.post.Post;
import com.example.communicatorproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @GetMapping(path = "/posts")
    public List<PostcardViewData> posts(){
        return postService.getPosts(false);
    }

    @GetMapping(path = "/post-full/{pid}")
    public Post posts(@PathVariable("pid") final Long pid){
        return postRepository.findById(pid).get();
    }

    @PostMapping("/post-reply")
    public ResponseEntity<String> postReply(@RequestParam("replyMessage") final String message,
                                            @RequestParam("replyTargetUserId") final Long targetUserId,
                                            @RequestParam("postId") final Long postId){
        return postService.postReply(message, targetUserId, postId);
    }

    @GetMapping("/postcard/{pid}")
    public PostcardViewData getPostcard(@PathVariable("pid") final Long pid){
        return postService.initPostcardViewData(pid);
    }


}
