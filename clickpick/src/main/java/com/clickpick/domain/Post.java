package com.clickpick.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createAt;
    private String position;
    private String content;
    private String title;
    @Column(name = "view_count")
    private int count;
    @Column(name = "hash_tag")
    private String hashtag;
    @Column(name = "photo_date")
    private LocalDateTime photoDate;
    private int liked;

    // 이미지 넣어야함
}
