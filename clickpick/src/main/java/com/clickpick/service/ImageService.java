package com.clickpick.service;

import com.clickpick.domain.ProfileImage;
import com.clickpick.domain.User;
import com.clickpick.repository.PostImageRepository;
import com.clickpick.repository.PostRepository;
import com.clickpick.repository.ProfileImageRepository;
import com.clickpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ProfileImageRepository profileImageRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    /* 프로필 사진 추가, 변경 */
    @Transactional
    public ResponseEntity createProfile(String userId, MultipartFile file) throws IOException {
        Optional<User> userResult = userRepository.findById(userId);
        if(userResult.isPresent()){
            User user = userResult.get();
            if(file.getContentType().startsWith("image") == false){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body("이미지만 업로드 가능합니다.");
            }
            Optional<ProfileImage> profileResult = profileImageRepository.findUserId(userId);
            if(profileResult.isPresent()){
                ProfileImage existingProfile = profileResult.get();
                deleteLocalProfileImage(existingProfile.getFilePath()+ "/" + existingProfile.getFileName());
                profileImageRepository.delete(profileResult.get());
                TransactionAspectSupport.currentTransactionStatus().flush();
                uploadProfileImage(file, user);
                return ResponseEntity.status(HttpStatus.OK).body("프로필사진이 변경되었습니다.");
            }

            uploadProfileImage(file, user);
            return ResponseEntity.status(HttpStatus.OK).body("프로필사진을 등록하였습니다.");

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원만 사용 가능한 기능입니다.");
    }

    /* 프로필 사진 제거 */
    @Transactional
    public ResponseEntity deleteProfile(String userId){
        Optional<User> userResult = userRepository.findById(userId);
        if(userResult.isPresent()){
            Optional<ProfileImage> profileResult = profileImageRepository.findUserId(userId);
            if(profileResult.isPresent()){
                ProfileImage existingProfile = profileResult.get();
                deleteLocalProfileImage(existingProfile.getFilePath()+ "/" + existingProfile.getFileName());
                profileImageRepository.delete(profileResult.get());
                return ResponseEntity.status(HttpStatus.OK).body("프로필사진이 삭제되었습니다.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로필 사진이 등록되어 있지 않습니다.");

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원만 사용 가능한 기능입니다.");
    }



    private void uploadProfileImage(MultipartFile file, User user) throws IOException {
        String fileName = user.getId() + "_" + file.getOriginalFilename();
        String filePath = uploadPath + "/profile";

        ProfileImage profileImage = new ProfileImage(user, fileName, filePath, file.getSize());
        profileImageRepository.save(profileImage);

        File saveFile = new File(filePath,fileName);

        file.transferTo(saveFile);
    }

    private void deleteLocalProfileImage(String filePath) {
        File fileToDelete = new File(filePath);
        if(fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

}
