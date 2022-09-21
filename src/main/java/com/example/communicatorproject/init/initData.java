package com.example.communicatorproject.init;

import com.example.communicatorproject.init.post.PostService;
import com.example.communicatorproject.model.post.Post;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class initData {

    private PostService postService;


    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Post post = Post.builder()
                    .text("I don't give a rat's ass what's happening.")
                    .userId(0L)
                    .username("Admin")
                    .avatar("http://127.0.0.1:8887/avatar.jpg")
                    .build();
            post = postService.save(post);

            Post reply = Post.builder()
                    .text("Reply")
                    .userId(1L)
                    .username("Reply username")
                    .replyToUsername("Admin")
                    .avatar("https://cdn-icons-png.flaticon.com/512/147/147144.png?w=1380&t=st=1663534147~exp=1663534747~hmac=974cb625a9e912472dce90b379b806967049f9500ce9f4b9ea709eb6b32e3ac0")
                    .replyToId(post.getId())
                    .build();
            reply.setParentPostId(post.getId());
            reply = postService.save(reply);
            post.addReply(reply);

            Post reply1 = Post.builder()
                    .text("Reply2")
                    .userId(2L)
                    .username("Reply username2")
                    .replyToUsername("Admin")
                    .avatar("https://cdn-icons-png.flaticon.com/512/147/147144.png?w=1380&t=st=1663534147~exp=1663534747~hmac=974cb625a9e912472dce90b379b806967049f9500ce9f4b9ea709eb6b32e3ac0")
                    .replyToId(post.getId())
                    .build();
            reply1.setParentPostId(post.getId());
            reply1 = postService.save(reply1);
            post.addReply(reply1);


            reply1.setParentPostId(post.getId());

            postService.save(post);

            Post post2 = Post.builder()
                    .text("This 's dome weird shit")
                    .userId(0L)
                    .username("Admin")
                    .avatar("http://127.0.0.1:8887/avatar.jpg")
                    .build();
            post2 = postService.save(post2);

            postService.save(post2);

        };
    }
}
