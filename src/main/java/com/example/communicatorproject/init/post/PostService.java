package com.example.communicatorproject.init.post;

import com.example.communicatorproject.model.formdata.PostcardViewData;
import com.example.communicatorproject.model.post.Post;
import com.example.communicatorproject.repository.PostRepository;
import com.example.communicatorproject.service.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService implements BaseService<Post> {


    private PostRepository postRepository;


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAll(Sort sort) {
        return postRepository.findAll(sort);
    }

    @Override
    public List<Post> findAllById(List<Long> ids) {
        return postRepository.findAllById(ids);
    }

    @Override
    public <S extends Post> List<S> saveAll(Iterable<S> entities) {
        return postRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        postRepository.flush();
    }

    @Override
    public <S extends Post> S saveAndFlush(S entity) {
        return postRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Post> List<S> saveAllAndFlush(Iterable<S> entities) {
        return postRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Post> entities) {
        postRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        postRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        postRepository.deleteAllInBatch();
    }

    @Override
    public Post getReferenceById(Long id) {
        return postRepository.getReferenceById(id);
    }

    @Override
    public <S extends Post> List<S> findAll(Example<S> example) {
        return postRepository.findAll(example);
    }

    @Override
    public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
        return postRepository.findAll(example, sort);
    }

    @Override
    public Post save(Post entity) {
        return postRepository.save(entity);
    }

    public ResponseEntity<String> postReply(String replyMessage, Long replyTargetUserId, Long postId){
          Post post = Post.builder().userId(1L).username("Admin").replyToUsername("User name").avatar("http://127.0.0.1:8887/avatar.jpg").text(replyMessage).replyToId(replyTargetUserId).build();
          postRepository.save(post);
          Post parentPost = postRepository.findById(postId).get();
          parentPost.addReply(post);
          postRepository.save(parentPost);
          if(post.getId() != null){
              return ResponseEntity.ok().body("Post saved.");
          }
          return ResponseEntity.badRequest().body("Error");
    }

    public PostcardViewData initPostcardViewData(Long pid){
        Optional<Post> parentPostOpt = postRepository.findById(pid);
        PostcardViewData viewdata = new PostcardViewData();
        if(parentPostOpt.isPresent()){
            Post parentPost = parentPostOpt.get();
            Post post = parentPost;
            boolean postHasParent = parentPost.getParentPostId() != null;
            if(postHasParent){
                parentPostOpt = postRepository.findById(parentPost.getParentPostId());
                if(parentPostOpt.isPresent()){
                    parentPost = parentPostOpt.get();
                }
            }
            viewdata.setParentPost(parentPost);
            if(!postHasParent) {
                parentPost.getReplies().removeIf(Objects::isNull);
                viewdata.setReplies(parentPost.getReplies());
            }else{
                List<Post> postList = new ArrayList<>();
                postList.add(post);
                postList.addAll(post.getReplies());
                viewdata.setReplies(postList);
            }
            return viewdata;
        }
        return viewdata;
    }

    public List<PostcardViewData> getPosts(boolean includeReplies){
        List<Post> foundPosts = postRepository.findAllPosts();
        List<PostcardViewData> postcardViewDataList = new ArrayList<>();
        for(Post p : foundPosts){
            PostcardViewData postcardViewData = new PostcardViewData();
            var parentPostOpt = postRepository.findById(p.getParentPostId());
            parentPostOpt.ifPresent(postcardViewData::setParentPost);
            if(includeReplies) {
                postcardViewData.setReplies(p.getReplies());
            }
            postcardViewDataList.add(postcardViewData);
        }
        return postcardViewDataList;
    }

}
