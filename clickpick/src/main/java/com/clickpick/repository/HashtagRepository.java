package com.clickpick.repository;

import com.clickpick.domain.Hashtag;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

    @Query("select h from Hashtag h where h.post.id =:postId") //입력받은 postID가 postId 와 동일한 hashtag 선택
    Optional<List<Hashtag>> findPostHashtag(@Param("postId")Long postId);
}
