package com.clickpick.domain;

import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DynamicInsert
@Setter // 테스트로 열어둠 dto로 변경해야함
public class User {

    @Id
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'")
    private UserStatus status;

    // 이미지 변수 (프로필 사진)


}
