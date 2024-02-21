package com.clickpick.jparepository;

import com.clickpick.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository {
    private final EntityManager em;

    // 댓글 저장
    public void save(Comment comment){
        em.persist(comment);
    }


    // 게시글 아이디를 통한 댓글 리스트 검색
    public List<Comment> findByPostId(Long postId){
        return em.createQuery("select c from Comment c where c.post.id =: postId",Comment.class)
                .setParameter("postId",postId)
                .getResultList();

    }

    // 유저 아이디를 통한 댓글 리스트 검색
    public List<Comment> findByUserId(Long userId){
        return em.createQuery("select c from Comment c where c.user.id =: userId",Comment.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    //댓글 아이디를 이용한 해당 댓글 검색
    public Comment findById(Long id){
        return em.find(Comment.class,id);
    }


}
