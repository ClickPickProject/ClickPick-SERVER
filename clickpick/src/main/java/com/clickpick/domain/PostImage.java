package com.clickpick.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class PostImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private String returnUrl;


    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;


    public PostImage(User user, String fileName, String filePath, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.user = user;
    }

    public void addReturnUrl(String url){
        this.returnUrl = url;
    }

    public void addPost(Post post){ // 게시글 작성 시 이미지와 게시글 연결
        this.post = post;
    }
}
