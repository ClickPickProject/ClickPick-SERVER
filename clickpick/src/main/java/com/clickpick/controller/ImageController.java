package com.clickpick.controller;

import com.clickpick.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /* 프로필 사진 추가, 변경 */
    @PostMapping("/api/member/profileimage")
    public ResponseEntity uploadProfile(@RequestParam("image")MultipartFile file) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = imageService.createProfile(userId, file);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 프로필 사진 삭제 */
    @DeleteMapping("api/member/profileimage")
    public ResponseEntity dropProfile(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = imageService.deleteProfile(userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 프로필 사진 조회 */ //보여줄땐?

    @GetMapping(value = "api/member/profileimage")
    public ResponseEntity viewProfile(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = imageService.myProfile(userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 사진 추가 */
    @PostMapping("api/member/post/image")
    public ResponseEntity uploadPostImage(@RequestParam("image")MultipartFile file) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = imageService.createPostImage(userId, file);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 사진 삭제 */
    @DeleteMapping("/api/member/post/image/{image_name}")
    public ResponseEntity dropImage(@RequestParam("image_name")String imageName){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = imageService.deletePostImage(userId, imageName);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시글 사진 조회 */

    /* 베스트 게시글 리스트 사진 조회 */
}
