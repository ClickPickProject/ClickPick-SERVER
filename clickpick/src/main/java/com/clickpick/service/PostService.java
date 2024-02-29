package com.clickpick.service;

import com.clickpick.domain.Hashtag;
import com.clickpick.domain.Post;
import com.clickpick.domain.User;
import com.clickpick.dto.post.CreatePostReq;
import com.clickpick.repository.AdminRepository;
import com.clickpick.repository.HashtagRepository;
import com.clickpick.repository.PostRepository;
import com.clickpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //읽기 전용 메서드에서 사용 (데이터의 변경 x)
public class PostService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;


    @Transactional
    public ResponseEntity createPost(CreatePostReq createPostReq) {
        System.out.println("createPostReq.getUserId() = " + createPostReq.getUserId());
        Optional<User> result = userRepository.findById(createPostReq.getUserId()); //로그인 시 이용가능이므로 체크 안함
        System.out.println("result = " + result);
        if(result.isPresent()){
            User user = result.get();

            Post post = new Post(user,createPostReq.getPosition(),createPostReq.getContent(),createPostReq.getTitle());
            postRepository.save(post);

            if(createPostReq.getHashtag() != null) {
                String hash = createPostReq.getHashtag();
                String[] parts = hash.split("#");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        Hashtag hashtag = new Hashtag(post, user, part);
                        hashtagRepository.save(hashtag);
                    }
                }

            }

            return ResponseEntity.status(HttpStatus.OK).body("게시글이 등록되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일(아이디) 입니다.");



    }
}
