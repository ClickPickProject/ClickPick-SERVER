package com.clickpick.repository;

import com.clickpick.domain.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
}
