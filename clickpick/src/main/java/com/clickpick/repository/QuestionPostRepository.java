package com.clickpick.repository;

import com.clickpick.domain.QuestionPost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionPostRepository {
    private final EntityManager em;

    //질문 게시글 등록
    public void save(QuestionPost questionPost){
        em.persist(questionPost);
    }

    //유저 아이디를 통한 질문 게시글 검색
    public List<QuestionPost> findByUserId(String userId){
        return em.createQuery("select qp from QuestionPost qp where qp.user.id =: userId", QuestionPost.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    //질문 게시글 아이디를 통한 검색
    public QuestionPost findByQuestionPostId(Long id){
        return em.find(QuestionPost.class,id);
    }


    //질문 게시글 전체 검색
    public List<QuestionPost> findAll(){
        return em.createQuery("select qp from QuestionPost qp", QuestionPost.class)
                .getResultList();

    }
}
