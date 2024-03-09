package com.clickpick.service;

import com.clickpick.config.RedisUtil;
import com.clickpick.domain.User;
import com.clickpick.dto.user.*;
import com.clickpick.repository.UserRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final RedisUtil redisUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,MailService mailService, RedisUtil redisUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.redisUtil = redisUtil;
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
    public ResponseEntity checkId(String id) throws Exception {
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 ID 입니다.");
        }
        else{
            mailService.sendMessage(id); //이메일(아이디) 인증 시 사용
            return ResponseEntity.status(HttpStatus.OK).body("인증번호를 해당 이메일로 발송하였습니다.");
        }

    }

    /* 닉네임 중복 확인 */
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
    public ResponseEntity checkPhone(String phone) {
        Optional<User> result = userRepository.findByPhone(phone);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 전화번호 입니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("사용할 수 있는 전화번호입니다.");
        }
    }

    /*로그인 체크 아이디 일치 && 비밀번호 일치*/
    public ResponseEntity checkLogin(LoginReq loginReq) {
        Optional<User> result = userRepository.findById(loginReq.getId());
        /* 아이디가 존재하는 지 */
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("잘못된 아이디 또는 비밀번호 입니다.");
        }
        else{
            User chechUser = result.get();
            boolean matches = passwordEncoder.matches(loginReq.getPassword(), chechUser.getPassword());
            if (matches) {
               return ResponseEntity.status(HttpStatus.OK).body("로그인 되었습니다.");
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("잘못된 아이디 또는 비밀번호 입니다.");
            }

        }
    }

    /* 아이디 찾기 */
    public ResponseEntity findId(FindIdReq findIdReq){
        Optional<User> result = userRepository.findByPhone(findIdReq.getPhone());
        if (result.isPresent()) {

            User user = result.get();

            if (findIdReq.getName().equals(user.getName())) {
                JsonObject jo = new JsonObject();
                jo.addProperty("id",user.getId());
                return ResponseEntity.status(HttpStatus.OK).body(jo.toString());
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 전화번호 및 이름입니다.");
    }

    /* 비밀번호 찾기 */
    public ResponseEntity findPassword(FindPwReq findPwReq) throws Exception {
        Optional<User> result = userRepository.findById(findPwReq.getId());
        if(result.isPresent()) {
            User user = result.get();
            if(user.getPhone().equals(findPwReq.getPhone()) && user.getName().equals(findPwReq.getName())){
                mailService.sendMessage(user.getId());
                return ResponseEntity.status(HttpStatus.OK).body("해당 이메일(아이디)로 인증번호를 발송하였습니다.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일(아이디) 입니다.");

    }

    /* 인증코드 확인 */
    @Transactional
    public ResponseEntity checkVerificationCode(VerifyCodeReq verifyCodeReq){
        Optional<String> redisCodeOpt = Optional.ofNullable(redisUtil.getData(verifyCodeReq.getCode()));

        if(redisCodeOpt.isPresent()){
            String redisCode = redisCodeOpt.orElse("");
            
            if(redisCode.equals(verifyCodeReq.getId())) {
                redisUtil.deleteData(verifyCodeReq.getCode());
                return ResponseEntity.status(HttpStatus.OK).body("인증에 성공하였습니다.");
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("인증번호가 일치하지 않습니다.");


    }

    /* 비밀번호 변경 */
    @Transactional
    public ResponseEntity changeNewPassword(ChangePasswordReq changePasswordReq) {
        Optional<User> result = userRepository.findById(changePasswordReq.getId());
        if(result.isPresent()){
            User user = result.get();
            if(passwordEncoder.matches(changePasswordReq.getPassword(), user.getPassword())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("기존 비밀번호와 동일합니다.");
            }
            else {
                user.updatePassword(passwordEncoder.encode(changePasswordReq.getPassword()));
                return ResponseEntity.status(HttpStatus.OK).body("비밀번호를 변경하였습니다.");
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일(아이디)입니다.");


    }
}
