package com.clickpick.jwt;

import com.clickpick.domain.User;
import com.clickpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findById(username);
        if(result.isPresent()){
            JWTUserDto jwtUserDto = new JWTUserDto(result.get().getId(),result.get().getPassword(),result.get().getStatus().toString());
            return new CustomUserDetails(jwtUserDto);
        }

        return null;
    }
}
