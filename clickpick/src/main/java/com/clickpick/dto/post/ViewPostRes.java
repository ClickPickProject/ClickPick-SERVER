package com.clickpick.dto.post;


import com.clickpick.domain.Hashtag;
import com.clickpick.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ViewPostRes {

    private Long postId;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime date;
    private Long likeCount;
    private Long viewCount;
    private String position;
    private LocalDateTime photoDate;
    private List<String> hashtags = new ArrayList<>();
    private String postCategory;
    private Long commentCount;


    public ViewPostRes(String nickname, Long likeCount, Post post){
        this.postId = post.getId();
        this.nickname = nickname;
        this.title = post.getTitle();
        this.content = post.getContent();
        this.date = post.getCreateAt();
        this.likeCount = likeCount;
        this.viewCount = post.getViewCount();
        this.position = post.getPosition();
        this.photoDate = post.getPhotoDate();
        this.postCategory = post.getPostCategory().toString();
        this.commentCount = (long) post.getComments().size();
    }

    public void addHashtag(String hashtag) {
        hashtags.add(hashtag);
    }


}
