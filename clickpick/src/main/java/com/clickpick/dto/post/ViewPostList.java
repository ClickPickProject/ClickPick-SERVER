package com.clickpick.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ViewPostList {

    private Long postId;
    private String nickname;
    private String title;
    private LocalDateTime createAt;
    private Long viewCount;
    private Long likeCount;
    private String hashtags;
    private String postCategory;

    public ViewPostList(Long postId,String nickname, String title, LocalDateTime createAt, Long viewCount, Long likeCount, String hashtags, String postCategory) {
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
