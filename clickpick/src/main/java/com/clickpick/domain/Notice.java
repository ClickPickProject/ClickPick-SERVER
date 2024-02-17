package com.clickpick.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notice {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String title;
    private String content;
    private LocalDateTime createdAt;
}
