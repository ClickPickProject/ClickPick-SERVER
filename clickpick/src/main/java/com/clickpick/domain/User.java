package com.clickpick.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","password","name","nickname","phone","createAt","status"})
public class User {

    @Id
    private String id; // 이메일 형식
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String phone;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'")
    private UserStatus status;

    // 이미지 변수 (프로필 사진)


    public User(String id, String password, String name, String nickname, String phone) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
    }

    //비밀번호 변경 시 사용 함수
    public void updatePassword(String password){
        this.password = password;

    }


}
