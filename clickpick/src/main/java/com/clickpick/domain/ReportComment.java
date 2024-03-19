package com.clickpick.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_comment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id",nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_user_id",nullable = false)
    private User reportUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id",nullable = false)
    private User reportedUser;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private String reason;

    public ReportComment(Comment comment, User reportUser, User reportedUser, String reason) {
        this.comment = comment;
        this.reportUser = reportUser;
        this.reportedUser = reportedUser;
        this.reason = reason;
    }
}
