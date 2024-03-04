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

    public ViewPostRes(String nickname, String title, String content, LocalDateTime date, Long likeCount, Long viewCount, String position, LocalDateTime photoDate) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.date = date;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.position = position;
        this.photoDate = photoDate;
    }

    public void addHashtag(String hashtag) {
        hashtags.add(hashtag);
    }


}
