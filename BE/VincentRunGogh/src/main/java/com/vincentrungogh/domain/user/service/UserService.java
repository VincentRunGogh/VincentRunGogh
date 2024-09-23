package com.vincentrungogh.domain.user.service;

import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.repository.UserRepository;
import com.vincentrungogh.domain.user.service.dto.request.UpdateUserProfileRequest;
import com.vincentrungogh.domain.user.service.dto.response.UserProfileResponse;
import com.vincentrungogh.global.auth.service.dto.response.UserPrincipal;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import com.vincentrungogh.global.service.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AwsService awsService;

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
            throw new CustomException(ErrorCode.INVALID_WEIGHT_AND_HEIGHT);
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

    public void updateProfileImage(int userId, MultipartFile image){
        // 1. aws 저장
        String imageUrl = awsService.uploadFile(image, userId);

        // 2. 유저 찾기
        User user = getUserById(userId);

        // 3. DB 저장
        user.updateProfileImage(imageUrl);
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
