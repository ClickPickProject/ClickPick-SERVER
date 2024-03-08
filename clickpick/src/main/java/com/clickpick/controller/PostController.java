package com.clickpick.controller;

import com.clickpick.dto.post.CreatePostReq;
import com.clickpick.dto.post.UpdatePostReq;
import com.clickpick.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /* 게시글 작성 */
    @PostMapping("/api/member/post")
    public ResponseEntity uploadPost(@RequestBody @Valid CreatePostReq createPostReq){ // 위치정보, 해시태그 없으면 null
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = postService.createPost(userId, createPostReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 게시글 삭제 */
    @DeleteMapping("/api/member/post/{postId}")
    public ResponseEntity erasePost(@PathVariable("postId")Long postId){ // 위치정보, 해시태그 없으면 null
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = postService.deletePost(postId, userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 수정 */
    @PostMapping("/api/member/post/{postId}")
    public ResponseEntity updatePost(@PathVariable("postId")Long postId,@RequestBody @Valid UpdatePostReq updatePostReq){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = postService.renewPost(postId, userId, updatePostReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 게시글 상세 조회 */
    @GetMapping("/api/post/{postId}")
    public ResponseEntity viewPost(@PathVariable("postId")Long postId){
        ResponseEntity responseEntity = postService.selectPost(postId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 좋아요 */
    @GetMapping("/api/member/likedpost/{postId}/{userId}")
    public  ResponseEntity likePost(@PathVariable("userId")String userId,@PathVariable("postId")Long postId){
        ResponseEntity responseEntity = postService.likeCount(userId,postId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 리스트 조회 */
    @GetMapping("/api/post/list")
    public ResponseEntity viewPostList(@RequestParam(required = false, defaultValue = "0", value = "page")int page){
        ResponseEntity responseEntity = postService.listPost(page);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 자신이 작성한 게시글 리스트 조회 */
    @GetMapping("/api/member/post/list")
    public ResponseEntity viewMyPostList(@RequestParam(required = false, defaultValue = "0", value = "page")int page){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = postService.myListPost(page,userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 베스트 게시글 리스트 조회 */
    @GetMapping("/api/post/list/best")
    public ResponseEntity viewBestPostList(@RequestParam(required = false, defaultValue = "0", value = "page")int page){
        ResponseEntity responseEntity = postService.bestListPost(page);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }





}
