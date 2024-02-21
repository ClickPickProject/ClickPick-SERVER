package com.clickpick.repository;

import com.clickpick.domain.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPostRepository extends JpaRepository<QuestionPost,Long> {
}
