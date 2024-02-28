package com.clickpick.service;

import com.clickpick.repository.AdminRepository;
import com.clickpick.repository.PostRepository;
import com.clickpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //읽기 전용 메서드에서 사용 (데이터의 변경 x)
public class PostService {
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private PostRepository postRepository;




}
