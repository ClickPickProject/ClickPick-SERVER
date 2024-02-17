package com.clickpick.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Alarm {

    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
    private Boolean read; // 가능? (디폴트 안 읽음)
    private LocalDateTime createAt;
}
