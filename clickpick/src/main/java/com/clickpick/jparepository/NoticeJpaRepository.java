package com.clickpick.jparepository;

import com.clickpick.domain.Notice;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeJpaRepository {
    private final EntityManager em;

    // 공지 등록
    public void save(Notice notice) {
        em.persist(notice);
    }

    // 공지 아이디를 통한 검색
    public Notice findById(Long id) {
        return em.find(Notice.class,id);
    }

    // 공지를 작성한 관리자 아이디를 통한 검색
    public List<Notice> findByAdmin(String adminId) {
        return em.createQuery("select n from Notice n where n.admin.id =: adminId",Notice.class)
                .setParameter("adminId",adminId)
                .getResultList();
    }

    //  전체 공지 검색
    public List<Notice> findAll() {
        return em.createQuery("select n from Notice n",Notice.class).getResultList();
    }

}
