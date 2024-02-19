package com.clickpick.repository;

import com.clickpick.domain.BanUser;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BanUserRepository {
    private final EntityManager em;


    // 정지 유저 추가
    public void save(BanUser banUser) {
        em.persist(banUser);
    }

    //유저 ID를 통한 정지 유저 확인
    public BanUser findById(String userId) {
        return em.createQuery("select b from BanUser b where b.user.id =: userId", BanUser.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }

    // 정지 시작일을 통한 정지 유저리스트 확인
    public List<BanUser> findByStartDate(LocalDateTime startDate) {
        return em.createQuery("select b from BanUser b where b.startDate =: startDate", BanUser.class)
                .setParameter("startDate",startDate)
                .getResultList();
    }

    // 정지 종료일을 통한 정지 유저리스트 확인
    public List<BanUser> findByEndDate(LocalDateTime endDate) {
        return em.createQuery("select b from BanUser b where b.endDate =: endDate", BanUser.class)
                .setParameter("endDate",endDate)
                .getResultList();
    }

    // 전체 정지 유저 검색

    public List<BanUser> findAll() {
        return em.createQuery("select b from  BanUser b",BanUser.class).getResultList();
    }
}
