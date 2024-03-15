package com.clickpick.controller;

import com.clickpick.dto.admin.CreateNoticeReq;
import com.clickpick.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeController {

    private final NoticeService adminService;

    @PostMapping("/api/admin/notice")
    public ResponseEntity uploadNotice(@RequestBody @Valid CreateNoticeReq createNoticeReq){
        String adminId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity responseEntity = adminService.createNotice(adminId, createNoticeReq);
        return  ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


}
