package com.clickpick.repository;

import com.clickpick.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
}
