package com.clickpick.jparepository;

import com.clickpick.domain.ReportComment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportCommentJpaRepository {
    private final EntityManager em;


    // 신고 댓글 저장
    public void save(ReportComment reportComment){
        em.persist(reportComment);
    }

    // 게시글 아이디를 통한 신고 게시글 검색
    public ReportComment findBytPostId(Long commentId){
        return em.createQuery("select rc from ReportComment rc where rc.comment.id =: commentId ",ReportComment.class)
                .setParameter("commentId",commentId)
                .getSingleResult();
    }

    // 유저 아이디를 통한 신고 게시글 검색 (여기서는 신고글을 작성한 유저)
    public List<ReportComment> findByUserId(String userId){
        return em.createQuery("select rc from ReportComment rc where rc.user.id =: userId ",ReportComment.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    //신고된 댓글 아이디를 통한 검색
    public ReportComment findByReportCommentId(Long id){
        return em.find(ReportComment.class,id);
    }


}
