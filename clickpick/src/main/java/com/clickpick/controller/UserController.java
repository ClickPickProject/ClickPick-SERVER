package com.clickpick.controller;

import com.clickpick.dto.LoginReq;
import com.clickpick.dto.SingUpReq;
import com.clickpick.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    /* 유저 회원 가입*/
    @PostMapping("/api/signup/user")
    public ResponseEntity singUpUser(@RequestBody @Valid SingUpReq singUpReq){
        ResponseEntity responseEntity = userService.join(singUpReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 아이디 중복 체크*/
    @GetMapping("/api/check/userid/{user_id}")
    public ResponseEntity duplicateId(@PathVariable("user_id")String userId){
        ResponseEntity responseEntity = userService.checkId(userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

    }

    /* 닉네임 중복 체크 */
    @GetMapping("/api/check/nickname/{nickname}")
    public ResponseEntity duplicateNickname(@PathVariable("nickname")String nickname){
        ResponseEntity responseEntity = userService.checkNickname(nickname);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 전화번호 중복 체크 */
    @GetMapping("/api/check/phone/{phone}")
    public ResponseEntity duplicatePhone(@PathVariable("phone")String phone){
        ResponseEntity responseEntity = userService.checkPhone(phone);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 유저 로그인 */
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody @Valid LoginReq loginReq){
        ResponseEntity responseEntity = userService.checkLogin(loginReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /*아이디 찾기*/

    /**/

}
