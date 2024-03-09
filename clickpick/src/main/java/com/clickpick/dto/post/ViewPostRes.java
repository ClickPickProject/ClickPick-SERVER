package com.clickpick.dto.post;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public ViewPostRes(String nickname, String title, String content, LocalDateTime date, Long likeCount, Long viewCount, String position, LocalDateTime photoDate, String postCategory) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.date = date;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.position = position;
        this.photoDate = photoDate;
        this.postCategory = postCategory;
    }

    public void addHashtag(String hashtag) {
        hashtags.add(hashtag);
    }


}
