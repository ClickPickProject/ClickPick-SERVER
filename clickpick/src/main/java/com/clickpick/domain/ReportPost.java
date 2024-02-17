package com.clickpick.domain;

import jakarta.persistence.*;

@Entity
public class ReportPost {
    @Id @GeneratedValue
    @Column(name = "report_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String reason;
}
