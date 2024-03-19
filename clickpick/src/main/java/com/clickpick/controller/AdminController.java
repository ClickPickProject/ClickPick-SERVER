package com.clickpick.controller;

import com.clickpick.dto.admin.SingUpAdminReq;
import com.clickpick.dto.user.SingUpReq;
import com.clickpick.service.AdminService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /* 매니저 회원 가입*/
    @PostMapping("/api/signup/admin")
    public ResponseEntity singUpAdmin(@RequestBody @Valid SingUpAdminReq singUpAdminReq){
        ResponseEntity responseEntity = adminService.join(singUpAdminReq);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }




}
