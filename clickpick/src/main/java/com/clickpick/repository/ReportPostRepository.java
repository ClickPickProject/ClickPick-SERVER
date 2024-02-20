package com.clickpick.repository;

import com.clickpick.domain.ReportPost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportPostRepository {
    private final EntityManager em;

    // 신고 게시글 저장
    public void save(ReportPost reportPost){
        em.persist(reportPost);
    }

    // 게시글 아이디를 통한 신고 게시글 검색
    public ReportPost findBytPostId(Long postId){
        return em.createQuery("select rp from ReportPost rp where rp.post.id =: postId ",ReportPost.class)
                .setParameter("postId",postId)
                .getSingleResult();
    }

    // 유저 아이디를 통한 신고 게시글 검색 (여기서는 신고글을 작성한 유저)
    public List<ReportPost> findByUserId(String userId){
        return em.createQuery("select rp from ReportPost rp where rp.user.id =: userId ",ReportPost.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    //신고된 게시글 아이디를 통한 검색
    public ReportPost findByReportPostId(Long id){
        return em.find(ReportPost.class,id);
    }


}
