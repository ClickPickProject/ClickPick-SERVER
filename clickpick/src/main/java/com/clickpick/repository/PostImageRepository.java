package com.clickpick.repository;

import com.clickpick.domain.PostImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query("select pi from PostImage pi where pi.user.id =:userId and pi.post.id =:postId and pi.fileName=:fileName")
    Optional<PostImage> findPostImage(@Param("userId")String userId, @Param("postId")Long postId, @Param("fileName")String fileName);
}
