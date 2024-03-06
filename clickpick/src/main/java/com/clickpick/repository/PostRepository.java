package com.clickpick.repository;

import com.clickpick.domain.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.user.id =:userId and p.id =:postId") // 띄어쓰기 주의 =:바로다음에 와야함
    Optional<Post> findUserPost(@Param("postId")Long postId, @Param("userId")String userId); // 유저가 작성한 게시글 조회

    List<Post> findByUserId(String userId); // 동일?



}
