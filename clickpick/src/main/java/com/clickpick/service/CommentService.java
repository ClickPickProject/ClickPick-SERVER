package com.clickpick.service;

import com.clickpick.domain.*;
import com.clickpick.dto.comment.CreateCommentReq;
import com.clickpick.repository.CommentLikeRepository;
import com.clickpick.repository.CommentRepository;
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
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    /* 댓글 작성 */
    @Transactional
    public ResponseEntity createComment(String userId, CreateCommentReq createCommentReq) {
        Optional<User> result = userRepository.findById(userId);
        if(result.isPresent()){
            User user = result.get();
            Optional<Post> postResult = postRepository.findById(createCommentReq.getPostId());
            if(postResult.isPresent()){
                Post post = postResult.get();
                Comment comment = new Comment(post,user,createCommentReq.getContent());
                commentRepository.save(comment);
                return ResponseEntity.status(HttpStatus.OK).body("댓글이 등록되었습니다.");
            }


            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일(아이디) 입니다.");
    }


    /* 댓글 삭제 */
    @Transactional
    public ResponseEntity deleteComment(String userId, Long commentId) {
        //유저와 게시글 id를 비교하여 동일한 값을 가져옴 => 없으면 본인이 작성하지 않음
        Optional<Comment> result = commentRepository.findUserComment(commentId, userId);

        if(result.isPresent()){
            commentRepository.delete(result.get());
            return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 삭제할 수 없는 댓글입니다.");

    }

    /* 댓글 수정 */
    @Transactional
    public ResponseEntity renewComment(String userId, Long commentId, CreateCommentReq updateCommentReq) {
        Optional<Comment> result = commentRepository.findUserComment(commentId, userId);

        if(result.isPresent()){
            Comment comment = result.get();
            comment.changeComment(updateCommentReq.getContent());

            return ResponseEntity.status(HttpStatus.OK).body("수정이 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 수정할 수 없는 댓글입니다.");

    }

    /* 댓글 좋아요 */
    @Transactional
    public ResponseEntity likeCount(String userId, Long commentId) {
        Optional<User> userResult = userRepository.findById(userId);
        if (userResult.isPresent()){

            Optional<Comment> commentResult = commentRepository.findById(commentId);
            if(commentResult.isPresent()){
                Comment comment = commentResult.get();
                Optional<CommentLike> commentLikeResult = commentLikeRepository.checkLikeComment(commentId, userId);
                if(commentLikeResult.isPresent()){
                    CommentLike commentLike = commentLikeResult.get();
                    commentLikeRepository.delete(commentLike);
                    return ResponseEntity.status(HttpStatus.OK).body("좋아요를 취소하였습니다.");
                }
                else {
                    CommentLike commentLike = new CommentLike(userResult.get(), commentResult.get());
                    commentLikeRepository.save(commentLike);
                    return ResponseEntity.status(HttpStatus.OK).body("해당 댓글을 좋아요 하였습니다.");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.");

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원만 가능한 기능입니다.");
    }
}
