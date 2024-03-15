package com.clickpick.service;

import com.clickpick.domain.Admin;
import com.clickpick.domain.Notice;
import com.clickpick.dto.admin.CreateNoticeReq;
import com.clickpick.repository.AdminRepository;
import com.clickpick.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public ResponseEntity createNotice(String admin_id, CreateNoticeReq createNoticeReq){

        Optional<Admin> result = adminRepository.findById(admin_id);  //로그인 시 이용가능이므로 체크 안함?

        if(result.isPresent()){  //사용자가 admin일 경우
            Admin admin = result.get();  //domain 가져옴
            Notice notice = new Notice(admin, createNoticeReq.getTitle(), createNoticeReq.getContent());
            noticeRepository.save(notice);
        }
        return ResponseEntity.status(HttpStatus.OK).body("공지사항이 등록되었습니다.");
    }

}
