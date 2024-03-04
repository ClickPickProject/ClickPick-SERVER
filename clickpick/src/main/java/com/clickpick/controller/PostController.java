package com.clickpick.controller;

import com.clickpick.domain.Hashtag;
import com.clickpick.dto.post.CreatePostReq;
import com.clickpick.dto.post.UpdatePostReq;
import com.clickpick.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /* 게시글 작성 */
    @PostMapping("/api/post")
    public ResponseEntity uploadPost(@RequestBody @Valid CreatePostReq createPostReq){ // 위치정보, 해시태그 없으면 null
        ResponseEntity responseEntity = postService.createPost(createPostReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 게시글 삭제 */
    @DeleteMapping("/api/post/{postId}/{userId}")
    public ResponseEntity erasePost(@PathVariable("postId")Long postId, @PathVariable("userId")String userId){ // 위치정보, 해시태그 없으면 null
        ResponseEntity responseEntity = postService.deletePost(postId, userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 수정 */
    @PostMapping("/api/post/{postId}") // userid api로 뺼까?
    public ResponseEntity updatePost(@PathVariable("postId")Long postId,@RequestBody @Valid UpdatePostReq updatePostReq){
        ResponseEntity responseEntity = postService.renewPost(postId,updatePostReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 게시글 상세 조회 */
    @GetMapping("/api/post/{postId}")
    public ResponseEntity viewPost(@PathVariable("postId")Long postId){
        ResponseEntity responseEntity = postService.selectPost(postId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
