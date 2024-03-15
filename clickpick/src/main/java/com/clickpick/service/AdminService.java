package com.clickpick.service;

import com.clickpick.domain.Admin;
import com.clickpick.domain.User;
import com.clickpick.dto.admin.SingUpAdminReq;
import com.clickpick.dto.user.SingUpReq;
import com.clickpick.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;


    /* 회원 가입
     *  현재 아이디 중복 가입을 막기 위해 id 체크 하도록 설정됨
     *  Front에서 모든 중복 및 빈칸 체크 후 가입버튼 활성화 한다면 해당 함수에서 중복체크 삭제 요망
     * */
    @Transactional
    public ResponseEntity join(SingUpAdminReq singUpAdminReq){
        String id = singUpAdminReq.getId();
        Optional<Admin> result = adminRepository.findById(id);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 ID 입니다.");
        }
        else{
            String encodedPassword = passwordEncoder.encode(singUpAdminReq.getPassword());
            Admin admin = new Admin(singUpAdminReq.getId(), encodedPassword,singUpAdminReq.getName(), singUpAdminReq.getPhone() );
            adminRepository.save(admin);
            return ResponseEntity.status(HttpStatus.OK).body("회원으로 가입되었습니다.");
        }

    }


}
