package com.clickpick.repository;

import com.clickpick.domain.Post;
import com.clickpick.domain.PostCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.user.id =:userId and p.id =:postId") // 띄어쓰기 주의 =:바로다음에 와야함
    Optional<Post> findUserPost(@Param("postId")Long postId, @Param("userId")String userId); // 유저가 작성한 게시글 조회

    @Query("select p from Post p where p.user.id =:userId")
    Page<Post> findUserId(@Param("userID")String userId, Pageable pageable);

    @Query(value = "SELECT * FROM post ORDER BY like_count DESC LIMIT 3", nativeQuery = true)
    List<Post> findTop3LikePost();

    @Query("select p from Post p where p.content like %:searchString%")
    Page<Post> findContent(@Param("searchString")String searchString, Pageable pageable);

    @Query("select p from Post p where p.user.nickname =:searchNickname")
    Page<Post> findNickname(@Param("searchNickname")String searchNickname, Pageable pageable);

    @Query("select p from Post p where p.title like %:searchTitle%")
    Page<Post> findTitle(@Param("searchTitle")String searchTitle, Pageable pageable);

    @Query("select p from Post p join p.hashtags ht where ht.content =:searchHashtag")
    Page<Post> findHashtag(@Param("searchHashtag")String searchHashtag, Pageable pageable);

    @Query("select p from Post p where p.postCategory =:searchCategory")
    Page<Post> findCategory(@Param("searchCategory") PostCategory searchCategory, Pageable pageable);






}
