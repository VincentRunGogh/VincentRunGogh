package com.vincentrungogh.domain.user.service;

import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.repository.UserRepository;
import com.vincentrungogh.domain.user.service.dto.request.UpdateUserProfileRequest;
import com.vincentrungogh.domain.user.service.dto.response.UserProfileResponse;
import com.vincentrungogh.global.auth.service.dto.response.UserPrincipal;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserProfileResponse getUserProfile(int userId){

        // 1. 유저 찾기
        User user = getUserById(userId);

        // 2. 반환
        return UserProfileResponse.createUserProfileResponse(user.getNickname(), user.getGender(),
                String.valueOf(user.getBirth()), user.getProfile(), user.getHeight(), user.getWeight());

    }

    public void updateUserProfile(int userId, UpdateUserProfileRequest request){
        // 1. 키 몸무게 0인지 확인
        if(request.getHeight() * request.getWeight() == 0){
            throw new CustomException(ErrorCode.FAILED_UPDATE_PROFILE);
        }

        // 2. 닉네임 중복 확인
        Optional<User> optionalUser = userRepository.findByNickname(request.getNickname());
        if(optionalUser.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }

        // 3. 유저 찾기
        User user = getUserById(userId);

        // 4. 저장
        user.updateProfile(request.getNickname(), request.getWeight(), request.getHeight());
        userRepository.save(user);
    }


    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            // 1. 사용자 확인
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            return UserPrincipal.createUserPrincipal(user.getId(), user.getEmail(), user.getPassword());
        } catch (Exception e){
            log.info("loadUserByUsername: " + e.getMessage());
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
