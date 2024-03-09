package com.clickpick.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewPostListRes {

    private Long postId;
    private String nickname;
    private String title;
    private LocalDateTime createAt;
    private Long viewCount;
    private Long likeCount;
    private String hashtags;
    private String postCategory;

    public ViewPostListRes(Long postId, String nickname, String title, LocalDateTime createAt, Long viewCount, Long likeCount, String hashtags, String postCategory) {
        this.postId = postId;
        this.nickname = nickname;
        this.title = title;
        this.createAt = createAt;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.hashtags = hashtags;
        this.postCategory = postCategory;
    }
}
