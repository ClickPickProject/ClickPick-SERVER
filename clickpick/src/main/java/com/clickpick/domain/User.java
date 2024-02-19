package com.clickpick.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DynamicInsert
public class User {

    @Id
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'")
    private UserStatus status;

    // 이미지 변수 (프로필 사진)


}
