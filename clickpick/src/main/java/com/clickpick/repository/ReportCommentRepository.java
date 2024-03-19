package com.clickpick.repository;

import com.clickpick.domain.ReportComment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {

    @Query("select rc from ReportComment rc where rc.reportedUser.nickname =:reportedUserNickname and rc.comment.id =:commentId ")
    Optional<ReportComment> findReportComment(@Param("reportedUserNickname")String reportedUserNickname, @Param("commentId") Long commentId);
}
