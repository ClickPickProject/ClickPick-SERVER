package com.clickpick.service;

import com.clickpick.domain.Hashtag;
import com.clickpick.domain.Post;
import com.clickpick.domain.User;
import com.clickpick.dto.post.CreatePostReq;
import com.clickpick.dto.post.UpdatePostReq;
import com.clickpick.dto.post.ViewPostRes;
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
                divideHashtagAndSave(createPostReq.getHashtag(), post, user);

            }

            return ResponseEntity.status(HttpStatus.OK).body("게시글이 등록되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일(아이디) 입니다.");



    }

    /* 게시글 삭제 */
    @Transactional
    public ResponseEntity deletePost(Long postId, String userId) {
        //유저와 게시글 id를 비교하여 동일한 값을 가져옴 => 없으면 본인이 작성하지 않음
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

    /* 게시글 수정 */
    @Transactional
    public ResponseEntity renewPost(Long postId,UpdatePostReq updatePostReq){
        Optional<Post> result = postRepository.findUserPost(postId, updatePostReq.getUserId());
        if(result.isPresent()){
            /* 게시글 중 제목, 내용, 위치 변경 */
            Optional<User> userResult = userRepository.findById(updatePostReq.getUserId());
            Post post = result.get();
            User user = userResult.get();
            post.changePost(updatePostReq.getTitle(), updatePostReq.getContent(), updatePostReq.getPosition());

            /* 게시글 중 해시 태그 수정 */
            Optional<List<Hashtag>> hashResult = hashtagRepository.findPostHashtag(postId);
            if(hashResult.isEmpty()){ // 게시글에 해시태그가 존재하지 않은 경우
                if(updatePostReq.getHashtag() != null) { // 추가 할 해시태그 존재
                    divideHashtagAndSave(updatePostReq.getHashtag(), post, user);

                }
            }
            else{ // 해시태그 존재하는 경우
                List<Hashtag> hashtags = hashResult.get();
                for (Hashtag hashtag : hashtags) {
                    hashtagRepository.delete(hashtag);
                }
                if(updatePostReq.getHashtag() != null) { // 추가 할 해시태그 존재
                    divideHashtagAndSave(updatePostReq.getHashtag(), post, user);
                }

            }
            return ResponseEntity.status(HttpStatus.OK).body("수정이 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 수정할 수 없는 게시글입니다.");

    }

    /* 게시글 상세 조회 */
    @Transactional
    public ResponseEntity selectPost(Long postId) {
        Optional<Post> postResult = postRepository.findById(postId);
        if(postResult.isPresent()){
            Post post = postResult.get();
            post.upViewCount(); //조회수 증가
            ViewPostRes viewPostRes = new ViewPostRes(post.getUser().getNickname(), post.getTitle(), post.getContent(), post.getCreateAt(), post.getLikeCount(), post.getViewCount(), post.getPosition(), post.getPhotoDate());
            Optional<List<Hashtag>> hashResult = hashtagRepository.findPostHashtag(postId);
            if(hashResult.isPresent()){
                List<Hashtag> hashtags = hashResult.get();
                for (Hashtag hashtag : hashtags) {
                    viewPostRes.addHashtag(hashtag.getContent());
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(viewPostRes);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
    }


    /* 해시태그 # 분리 후 테이블 저장 함수 */
    public void divideHashtagAndSave(String fullHashtag,Post post, User user){
        String hash = fullHashtag.replaceAll(" ", ""); // 공백 제거
        String[] parts = hash.split("#");
        for (String part : parts) {
            if (!part.isEmpty()) {
                Hashtag hashtag = new Hashtag(post, user, part);
                hashtagRepository.save(hashtag);
            }
        }
    }
}
