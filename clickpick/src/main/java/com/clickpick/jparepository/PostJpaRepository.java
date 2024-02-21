package com.clickpick.jparepository;

import com.clickpick.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {
    private final EntityManager em;

    // 게시글 저장
    public void save(Post post) {
        em.persist(post);
    }

    // 게시글 아이디를 통한 검색
    public Post findById(Long id) {
        return em.find(Post.class,id);
    }

    // 게시글 전체 검색
    public List<Post> findAll(){
        return em.createQuery("select p from Post p",Post.class).getResultList();
    }

    // 게시글 작성자 Id를 통한 검색
    public List<Post> findByUserId(String userId){
        return em.createQuery("select p from Post p where p.user.id =: userId",Post.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    // 작성된 게시글 위치를 통한 검색 (방식 2가지 예상 완벽 일치, 일부 범위 ) => 현재는 완벽 일치

    public List<Post> findByPosition(String position) {
        return em.createQuery("select p from Post p where p.position =: position",Post.class)
                .setParameter("position",position)
                .getResultList();
    }


    // 작성된 게시글의 제목을 포함한 게시글 검색
    public List<Post> findByTitle(String title){
        return em.createQuery("select p from Post p where p.title like : title",Post.class)
                .setParameter("title","%" + title + "%")
                .getResultList();
    }

    //해시태그 테이블 분리해야함 -> 다수의 값을 가짐 -> 정규화 진행 필요

}
