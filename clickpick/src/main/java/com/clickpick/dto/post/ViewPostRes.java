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

    public ViewPostRes(String nickname, String title, String content, LocalDateTime date, Long likeCount, Long viewCount, String position, LocalDateTime photoDate, String postCategory, Long commentCount) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.date = date;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.position = position;
        this.photoDate = photoDate;
        this.postCategory = postCategory;
        this.commentCount = commentCount;
    }
    public ViewPostRes(String nickname, Long likeCount, Post post){
        this.nickname = nickname;
        this.title = post.getTitle();
        this.content = post.getContent();
        this.date = post.getCreateAt();
        this.likeCount = likeCount;
        this.viewCount = post.getViewCount();
        this.position = post.getPosition();
        this.photoDate = post.getPhotoDate();
        this.postCategory = post.getPostCategory().toString();
        this.commentCount = post.getCommentCount();
    }

    public void addHashtag(String hashtag) {
        hashtags.add(hashtag);
    }


}
