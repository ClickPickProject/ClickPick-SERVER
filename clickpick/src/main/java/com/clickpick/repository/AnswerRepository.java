package com.clickpick.repository;

import com.clickpick.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository <Answer, Long> {
}
