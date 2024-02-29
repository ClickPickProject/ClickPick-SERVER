package com.clickpick.repository;

import com.clickpick.domain.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.user.id =: userId ")
    List<Post> findUserPost(@Param("userId") String userId); // 유저가 작성한 게시글 조회

    List<Post> findByUserId(String userId); // 동일?


}
