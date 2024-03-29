package com.clickpick.jparepository;

import com.clickpick.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJpaRepository {


    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(String id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByName(String name) {
        return em.createQuery("select u from User u where u.name =: name", User.class)
                .setParameter("name",name)
                .getResultList();
    }
    public User findByPhone(String phone) {
        return em.createQuery("select u from User u where u.phone =: phone", User.class)
                .setParameter("phone",phone).getSingleResult();
    }


}
