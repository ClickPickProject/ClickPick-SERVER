package com.clickpick.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class User {

    @Id
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private String phone;
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // 이미지 변수 (프로필 사진)


}
