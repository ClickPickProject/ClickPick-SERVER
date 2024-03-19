package com.clickpick.service;

import com.clickpick.domain.Admin;
import com.clickpick.domain.Hashtag;
import com.clickpick.domain.Notice;
import com.clickpick.domain.Post;
import com.clickpick.dto.admin.CreateNoticeReq;
import com.clickpick.dto.admin.UpdateNoticeReq;
import com.clickpick.repository.AdminRepository;
import com.clickpick.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;

    /* 공지글 작성 */
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

    /* 공지글 삭제 */
    @Transactional
    public ResponseEntity deleteNotice(Long noticeId, String adminId) {
        Optional<Notice> result = noticeRepository.findAdminNotice(noticeId, adminId);

        if(result.isPresent()){
            noticeRepository.delete(result.get());
            return ResponseEntity.status(HttpStatus.OK).body("공지사항 삭제가 완료되었습니다. ");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 삭제할 수 없는 게시글입니다.");
    }

    @Transactional
    public ResponseEntity renewNotice(Long noticeId, String adminId, UpdateNoticeReq updateNoticeReq) {
        Optional<Notice> result = noticeRepository.findAdminNotice(noticeId, adminId);

        if(result.isPresent()){
            /* 게시글 중 제목, 내용변경 */
            //Optional<User> userResult = userRepository.findById(userId);

            Notice notice = result.get();

            notice.ChangePost(updateNoticeReq.getTitle(), updateNoticeReq.getContent());


            return ResponseEntity.status(HttpStatus.OK).body("수정이 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 수정할 수 없는 게시글입니다.");


    }
}
