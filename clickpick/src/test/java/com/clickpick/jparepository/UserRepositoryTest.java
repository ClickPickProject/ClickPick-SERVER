package com.clickpick.jparepository;

import com.clickpick.domain.User;
import com.clickpick.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EntityManager em;

    @Test
    public void basicCRUD() {
        User user1 = new User("user1","123","kk","sssss","123213213");
        User user2 = new User("user2","1321","dd","dddd","21312412412421");
        userRepository.save(user1);
        userRepository.save(user2);


        //단건 조회 검증
        User findUser1 = userRepository.findById(user1.getId()).get();
        User findUser2 = userRepository.findById(user2.getId()).get();
        assertThat(findUser1).isEqualTo(user1);
        assertThat(findUser2).isEqualTo(user2);


        //리스트 조회 검증
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(2);


        //카운트 검증
        long count = userRepository.count();
        assertThat(count).isEqualTo(2);



        //삭제 검증

    }

    @Test
    public void encoderTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String pass1 = bCryptPasswordEncoder.encode("1234");
        String pass2 = bCryptPasswordEncoder.encode("1234");
        System.out.println("pass1 = " + pass1);
        System.out.println("pass2 = " + pass2);

        assertThat(bCryptPasswordEncoder.matches("1234",pass1)).isTrue(); // Salt값으로 계산 하여 비교
        assertThat(bCryptPasswordEncoder.matches("12345",pass2)).isTrue();

    }



}
