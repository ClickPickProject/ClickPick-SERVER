package com.clickpick.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
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
    @Column(name = "photo_date")
    private LocalDateTime photoDate;
    @Column(name = "post_category")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'자유'")
    private PostCategory postCategory;
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Hashtag> hashtags = new ArrayList<>(); //
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    // 이미지 넣어야함


    public Post(User user, String position, String content, String title, String category) {
        this.user = user;
        this.position = position;
        this.content = content;
        this.title = title;
        this.postCategory = PostCategory.valueOf(category);
    }

    public void changePost(String title, String content, String position, String category){
        this.title = title;
        this.content = content;
        this.position = position;
        this.postCategory = PostCategory.valueOf(category);
    }

    public void upViewCount(){
        this.viewCount += 1;
    }



}
