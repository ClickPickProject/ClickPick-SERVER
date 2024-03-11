package com.clickpick.repository;

import com.clickpick.domain.PostLike;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query("select pl from PostLike pl where pl.user.id =:userId and pl.post.id =:postId")
    Optional<PostLike> checkLikePost(@Param("postId")Long postId, @Param("userId")String userId);

    long countByPostId(Long postId);
}
