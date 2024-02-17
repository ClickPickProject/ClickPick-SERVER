package com.clickpick.domain;

import jakarta.persistence.*;

@Entity
public class ReportComment {

    @Id @GeneratedValue
    @Column(name = "report_comment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String reason;
}
