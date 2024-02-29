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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //읽기 전용 메서드에서 사용 (데이터의 변경 x)
public class PostService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;


    /* 게시글 작성 */
    @Transactional
    public ResponseEntity createPost(CreatePostReq createPostReq) {
        Optional<User> result = userRepository.findById(createPostReq.getUserId()); //로그인 시 이용가능이므로 체크 안함
        if(result.isPresent()){
            User user = result.get();

            Post post = new Post(user,createPostReq.getPosition(),createPostReq.getContent(),createPostReq.getTitle());
            postRepository.save(post);

            /* 해시태그 # 기준으로 분리 후 저장*/
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

    /* 게시글 삭제 */
    @Transactional
    public ResponseEntity deletePost(Long postId, String userId) {
        Optional<Post> result = postRepository.findUserPost(postId, userId);

        if(result.isPresent()){
            /* 해시태그 존재 시 삭제*/
            Optional<List<Hashtag>> hashResult = hashtagRepository.findPostHashtag(postId);
            if(hashResult.isPresent()){
                List<Hashtag> hashtags = hashResult.get();
                for (Hashtag hashtag : hashtags) {
                    hashtagRepository.delete(hashtag);
                }
            }
            postRepository.delete(result.get());
            return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 삭제할 수 없는 게시글입니다.");

    }
}
