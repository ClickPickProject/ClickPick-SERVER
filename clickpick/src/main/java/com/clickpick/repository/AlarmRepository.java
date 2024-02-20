package com.clickpick.repository;

import com.clickpick.domain.Alarm;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmRepository {
    private final EntityManager em;


    //알람 저장
    public void save(Alarm alarm){
        em.persist(alarm);
    }

    // 알람 아이디를 통한 검색
    public Alarm findById(Long Id){
        return em.find(Alarm.class,Id);
    }

    // 사용자 아이디를 통한 검색
    public List<Alarm> findByUserId(String userId){
        return  em.createQuery("select a from Alarm a where a.user.id =: userId", Alarm.class)
                .setParameter("userId",userId)
                .getResultList();
    }
}
