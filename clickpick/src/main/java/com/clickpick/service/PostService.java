package com.clickpick.service;

import com.clickpick.domain.*;
import com.clickpick.dto.post.CreatePostReq;
import com.clickpick.dto.post.UpdatePostReq;
import com.clickpick.dto.post.ViewPostListRes;
import com.clickpick.dto.post.ViewPostRes;
import com.clickpick.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final PostLikeRepository postLikeRepository;


    /* 게시글 작성 */
    @Transactional
    public ResponseEntity createPost(String userId, CreatePostReq createPostReq) {
        Optional<User> result = userRepository.findById(userId); //로그인 시 이용가능이므로 체크 안함
        if(isEnumValue(createPostReq.getPostCategory())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하지 않는 카테고리 입니다.");
        }
        if(result.isPresent()){
            User user = result.get();
            Post post = new Post(user,createPostReq.getPosition(),createPostReq.getContent(),createPostReq.getTitle(),createPostReq.getPostCategory());
            postRepository.save(post);
            System.out.println("createPostReq = " + createPostReq.getHashtags());
            if(createPostReq.getHashtags() != null){
                for (String hashtag : createPostReq.getHashtags()) {
                    Hashtag addHashtag = new Hashtag(post,hashtag);
                    hashtagRepository.save(addHashtag);
                }
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
            postRepository.delete(result.get());
            return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 삭제할 수 없는 게시글입니다.");

    }

    /* 게시글 수정 */
    @Transactional
    public ResponseEntity renewPost(Long postId, String userId, UpdatePostReq updatePostReq){
        Optional<Post> result = postRepository.findUserPost(postId, userId);
        if(isEnumValue(updatePostReq.getPostCategory())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하지 않는카테고리입니다.");
        }
        if(result.isPresent()){
            /* 게시글 중 제목, 내용, 위치 변경 */
            //Optional<User> userResult = userRepository.findById(userId);
            Post post = result.get();
            //User user = userResult.get();
            post.changePost(updatePostReq.getTitle(), updatePostReq.getContent(), updatePostReq.getPosition(), updatePostReq.getPostCategory());

            /* 게시글 중 해시 태그 수정 */
            Optional<List<Hashtag>> hashResult = hashtagRepository.findPostHashtag(postId);
            if(hashResult.isEmpty()){ // 게시글에 해시태그가 존재하지 않은 경우
                if(updatePostReq.getHashtags().size() > 0) { // 추가 할 해시태그 존재
                    for (String hashtag : updatePostReq.getHashtags()) {
                        Hashtag addHashtag = new Hashtag(post,hashtag);
                        hashtagRepository.save(addHashtag);
                    }

                }
            }
            else{ // 해시태그 존재하는 경우
                List<Hashtag> hashtags = hashResult.get();
                for (Hashtag hashtag : hashtags) {
                    hashtagRepository.delete(hashtag);
                }
                if(updatePostReq.getHashtags() != null) { // 추가 할 해시태그 존재
                    for (String hashtag : updatePostReq.getHashtags()) {
                        Hashtag addHashtag = new Hashtag(post,hashtag);
                        hashtagRepository.save(addHashtag);
                    }
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
            post.upViewCount(); //조회수 증가 ->@Transaction 필요
            ViewPostRes viewPostRes = new ViewPostRes(post.getUser().getNickname(), postLikeRepository.countByPostId(postId), post);

            if(post.getHashtags().size()>0){
                for(Hashtag hashtag : post.getHashtags()){
                    viewPostRes.addHashtag(hashtag.getContent());
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(viewPostRes);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
    }

    /* 게시글 좋아요 */
    @Transactional
    public ResponseEntity likeCount(String userId, Long postId) {

        Optional<User> userResult = userRepository.findById(userId);
        if (userResult.isPresent()){

            Optional<Post> postResult = postRepository.findById(postId);
            if(postResult.isPresent()){
                Post post = postResult.get();
                Optional<PostLike> postLikeResult = postLikeRepository.checkLikePost(postId, userId);
                if(postLikeResult.isPresent()){
                    PostLike postLike = postLikeResult.get();
                    postLikeRepository.delete(postLike);
                    post.downLikeCount();

                    return ResponseEntity.status(HttpStatus.OK).body("좋아요를 취소하였습니다.");
                }
                else {
                    PostLike postLike = new PostLike(userResult.get(),postResult.get());
                    post.upLikeCount();
                    postLikeRepository.save(postLike);
                    return ResponseEntity.status(HttpStatus.OK).body("해당 게시글을 좋아요 하였습니다.");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원만 가능한 기능입니다.");
    }

    /* 게시글 전체 리스트 조회 */
    public ResponseEntity listPost(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC,"createAt"));
        Page<Post> pagingResult = postRepository.findAll(pageRequest);
        Page<ViewPostListRes> map = pagingResult.map(post -> new ViewPostListRes(post));

    return ResponseEntity.status(HttpStatus.OK).body(map);

    }

    /* 자신이 작성한 게시글 리스트 조회 */
    public ResponseEntity myListPost(int page, String userId){
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC,"createAt"));
        Page<Post> pagingResult = postRepository.findUserId(userId, pageRequest);
        Page<ViewPostListRes> map = pagingResult.map(post -> new ViewPostListRes(post));

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /* 좋아요가 많은 게시글 리스트 조회(3개) */
    public ResponseEntity bestListPost(){
        List<Post> bestPosts = postRepository.findTop3LikePost();
        List<ViewPostListRes> viewPostListResList = new ArrayList<>();

        if(bestPosts.size() > 0){
            for (Post bestPost : bestPosts) {
                ViewPostListRes viewPostListRes = new ViewPostListRes(bestPost);
                viewPostListResList.add(viewPostListRes);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(viewPostListResList);
    }


    public static boolean isEnumValue(String category) {
        try {
            // Enum.valueOf() 메서드를 사용하여 입력값이 Enum 타입에 속하는지 확인
            PostCategory postCategory = Enum.valueOf(PostCategory.class, category);
            return false; // 속한다면 false 반환
        } catch (IllegalArgumentException e) {
            return true; // 속하지 않는다면 true 반환
        }
    }
}
