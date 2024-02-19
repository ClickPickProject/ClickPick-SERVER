package com.clickpick.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;
    private String position;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String title;
    @Column(name = "view_count",nullable = false)
    @ColumnDefault("0")
    private Long viewCount;
    @Column(name = "hash_tag")
    private String hashtag;
    @Column(name = "photo_date")
    private LocalDateTime photoDate;
    @Column(name = "like_count",nullable = false)
    @ColumnDefault("0")
    private Long likeCount;

    // 이미지 넣어야함
}
