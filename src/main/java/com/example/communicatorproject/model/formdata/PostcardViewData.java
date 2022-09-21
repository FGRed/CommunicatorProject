package com.example.communicatorproject.model.formdata;

import com.example.communicatorproject.model.post.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostcardViewData {
    Post parentPost;
    List<Post> replies;
}
