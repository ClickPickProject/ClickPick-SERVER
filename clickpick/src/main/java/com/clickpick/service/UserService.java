package com.clickpick.service;

import com.clickpick.domain.ReportPost;
import com.clickpick.domain.User;
import com.clickpick.dto.SingUpReq;
import com.clickpick.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    /* 회원 가입
    *  현재 아이디 중복 가입을 막기 위해 id 체크 하도록 설정됨
    *  Front에서 모든 중복 및 빈칸 체크 후 가입버튼 활성화 한다면 해당 함수에서 중복체크 삭제 요망
    * */
    @Transactional
    public ResponseEntity join(SingUpReq singUpReq){
        String id = singUpReq.getId();
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 ID 입니다.");
        }
        else{
            String encodedPassword = passwordEncoder.encode(singUpReq.getPassword());
            User user = new User(singUpReq.getId(), encodedPassword,singUpReq.getName(),singUpReq.getNickname(),singUpReq.getPhone());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("회원으로 가입되었습니다.");
        }

    }

    /* 아이디(이메일) 중복 확인 */
    @Transactional
    public ResponseEntity checkId(String id){
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 ID 입니다.");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("사용할 수 있는 ID 입니다.");
        }

    }

    /* 닉네임 중복 확인 */
    @Transactional
    public ResponseEntity checkNickname(String nickname){
        Optional<User> result = userRepository.findByNickname(nickname);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하는 별명입니다.");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("사용할 수 있는 별명입니다.");
        }
    }

    /* 전화번호 중복 확인 */
    @Transactional
    public ResponseEntity checkPhone(String phone) {
        Optional<User> result = userRepository.findByPhone(phone);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 전화번호 입니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("사용할 수 있는 전화번호입니다.");
        }
    }




}
