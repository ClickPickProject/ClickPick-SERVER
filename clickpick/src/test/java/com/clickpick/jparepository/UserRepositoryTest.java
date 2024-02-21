package com.clickpick.jparepository;

import com.clickpick.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserJpaRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    void 회원가입() throws Exception {
        //given
        User user = new User();

        user.setId("taemintaemin");
        user.setName("taemin");
        user.setEmail("taemin@naver.com");
        user.setPassword("123123");
        user.setNickname("태민");
        user.setPhone("01012345678");

        //when
        em.persist(user);


        //then
        em.flush();
        System.out.println(userRepository.findOne("taemintaemin"));

    }



}
