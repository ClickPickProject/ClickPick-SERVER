package com.clickpick.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@DynamicInsert
public class Alarm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'UNREAD'")
    private AlarmStatus status;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;
}
