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

import java.io.*;
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
    @Value("${custom.dns}")
    private String dns;


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

    /* 프로필 사진 조회 */
    public ResponseEntity myProfile(String userId) {
        Optional<User> userResult = userRepository.findById(userId);
        if(userResult.isPresent()){
            Optional<ProfileImage> profileResult = profileImageRepository.findUserId(userId);
            if(profileResult.isPresent()){ // 프로필이 있는 경우
                ProfileImage profileImage = profileResult.get();
                String ImagePath = profileImage.getFilePath() + "/" + profileImage.getFileName();
                String url = dns + "/profile/images/" + profileImage.getFileName();
                //byte[] fileArray = getImage(ImagePath);
                return ResponseEntity.status(HttpStatus.OK).body(url);

            }
            else{ // 프로필이 없는 경우
                String imagePath = uploadPath + "/profile/default.png";
                String url = dns + "/profile/images/" + "default.png";
                //byte[] fileArray = getImage(imagePath);

                return ResponseEntity.status(HttpStatus.OK).body(url);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원만 사용 가능한 기능입니다.");
    }

    /* 이미지 가져오는 함수 */
    private static byte[] getImage(String ImagePath) {
        InputStream in = null;
        try{
            in = new FileInputStream(ImagePath);
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = in.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
            fileArray = out.toByteArray();
            in.close();
            out.close();
        } catch(IOException e){
            throw new RuntimeException("이미지가 없습니다.");
        }
        return fileArray;
    }


    private void uploadProfileImage(MultipartFile file, User user) throws IOException {

        String fileName = user.getId() + "." +getFileExtension(file);
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

    public String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex > 0) {
                return originalFilename.substring(lastDotIndex + 1);
            }
        }
        return null; // 확장자가 없는 경우
    }

}
