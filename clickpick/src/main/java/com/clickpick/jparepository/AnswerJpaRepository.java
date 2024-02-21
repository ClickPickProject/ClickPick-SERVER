package com.clickpick.jparepository;

import com.clickpick.domain.Answer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnswerJpaRepository {
    private final EntityManager em;


    //답변 저장
    public void save(Answer answer){
        em.persist(answer);
    }


    // 관리자 아이디를 통한 답변 검색
    public List<Answer> findByAdminId(String adminId){
        return em.createQuery("select a from Answer a where a.admin.id =: adminId", Answer.class)
                .setParameter("adminId",adminId)
                .getResultList();
    }

    // 답변 아이디를 통한 답변 검색
    public Answer findById(Long id){
        return em.find(Answer.class,id);
    }

    //질문 아이디를 통한 답변 검색
    public Answer findByQuestionPostId(Long questionId){
        return em.createQuery("select a from Answer a where a.questionPost.id =: questionId", Answer.class)
                .setParameter("questionId", questionId)
                .getSingleResult();
    }

    //답변 전체 검색
    public List<Answer> findAll(){
        return em.createQuery("select a from Answer a", Answer.class)
                .getResultList();
    }



}
