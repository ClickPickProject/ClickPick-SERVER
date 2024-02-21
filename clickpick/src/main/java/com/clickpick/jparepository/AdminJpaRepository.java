package com.clickpick.jparepository;

import com.clickpick.domain.Admin;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminJpaRepository {

    private final EntityManager em;


    public void save(Admin admin) {
        em.persist(admin);
    }

    public Admin findOne(String id) {
        return em.find(Admin.class, id);
    }

    public List<Admin> findAll() {
        return em.createQuery("select a from Admin a", Admin.class).getResultList();
    }

    public List<Admin> findByName(String name) {
        return em.createQuery("select a from Admin a where a.name =: name", Admin.class)
                .setParameter("name",name)
                .getResultList();
    }

    public Admin findByPhone(String phone) {
        return em.createQuery("select a from Admin a where a.phone =: phone", Admin.class)
                .setParameter("phone",phone).getSingleResult();
    }
}
