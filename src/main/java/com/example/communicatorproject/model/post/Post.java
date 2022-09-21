package com.example.communicatorproject.model.post;

import com.example.communicatorproject.util.NumberUtil;
import com.example.communicatorproject.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String text;
    private String media;

    private Long userId;
    private String username;
    private String avatar;

    private String replyToUsername;
    private Long replyToId;

    private Long shares, replyCount, likes = 0L;

    @Transient
    private String sharesShort, timeAgo, replyCountShort, likesShort;

    @JsonBackReference
    private Long parentPostId;

    @OneToMany()
    @OrderColumn
    @JsonManagedReference
    private List<Post> replies = new ArrayList<>();

    public void addReply(Post post){
        if(post != null) {
            post.setParentPostId(this.id);
            if (this.replies == null) {
                this.replies = new ArrayList<>();
                this.replies.add(post);
            } else {
                this.replies.add(post);
            }
            this.replyCount = replies.stream().count();
        }
    }

    private Date creationDate, dateModified;

    @PrePersist
    void onPrePersist(){
        creationDate = new Date();
    }

    @PreUpdate
    void onPreUpdate(){
        dateModified = new Date();
    }

    @PostLoad
    void postLoad(){
        this.timeAgo = TimeUtil.calculateTimeAgoByTimeGranularity(this.creationDate);
        this.sharesShort = NumberUtil.format(this.shares);
        this.replyCountShort = NumberUtil.format(this.replyCount);
        this.likesShort = NumberUtil.format(this.likes);
    }
}
