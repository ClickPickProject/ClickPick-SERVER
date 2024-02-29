package com.clickpick.controller;

import com.clickpick.domain.Hashtag;
import com.clickpick.dto.post.CreatePostReq;
import com.clickpick.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}