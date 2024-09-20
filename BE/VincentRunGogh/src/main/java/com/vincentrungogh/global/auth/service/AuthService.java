package com.vincentrungogh.global.auth.service;

import com.vincentrungogh.domain.myhealth.entity.MyHealth;
import com.vincentrungogh.domain.myhealth.repository.MyHealthRepository;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.repository.UserRepository;
import com.vincentrungogh.global.auth.service.dto.request.CodeCheckRequest;
import com.vincentrungogh.global.auth.service.dto.request.LoginRequest;
import com.vincentrungogh.global.auth.service.dto.request.SignupRequest;
import com.vincentrungogh.global.auth.service.dto.response.CodeCheckResponse;
import com.vincentrungogh.global.auth.service.dto.response.FindDuplicatedResponse;
import com.vincentrungogh.global.auth.service.dto.response.LoginResponse;
import com.vincentrungogh.global.auth.service.dto.response.UserPrincipal;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import com.vincentrungogh.global.service.EmailService;
import com.vincentrungogh.global.service.RedisService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authorizationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MyHealthRepository myHealthRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        log.info("AuthService : 로그인 시작");

        // 1. 유효성 확인
        Authentication authentication = authorizationManager.authenticate(
                // 아이디, 패스워드 입력
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // 2. 유저 정보 가져오기
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // 3. 토큰 생성
        String accessToken = jwtService.buildAccessToken(userPrincipal.getId());
        String refreshToken = jwtService.buildRefreshToken(userPrincipal.getId());

        // 4. refreshToken cookie에 넣기
        jwtService.addRefreshTokenToCookie(response, refreshToken);

        // 5. redis 저장
        redisService.saveRefreshToken(userPrincipal.getId(), refreshToken);

        // 6. user 정보 가져오기
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return LoginResponse.createLoginResponse(accessToken, user.getIsChanged(), user.getNickname());
    }

    @Transactional
    public void signup(SignupRequest signupRequest) {
        // 1. 이메일 중복 확인
        if(checkDuplicateEmail(signupRequest.getEmail())){
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
        // 2. 닉네임 중복 확인
        if(checkDuplicateNickname(signupRequest.getNickname())){
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
        // 3. 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        // 4. User 엔티티 생성
        User user = User.createUser(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getNickname(),
                signupRequest.getGender(),
                signupRequest.getBirth(),
                signupRequest.getHeight(),
                signupRequest.getWeight());
        // 5. user 저장
        userRepository.save(user);
        // 6. myHealth 저장
        MyHealth myHealth = MyHealth.createMyHealth(user);
        myHealthRepository.save(myHealth);

        return;
    }

    public FindDuplicatedResponse findNickname(String nickname){
        // 1. 닉네임 길이 확인
        if(nickname.length() > 10){
            throw new CustomException(ErrorCode.INVALID_NICKNAME_LENGTH);
        }
        // 2. 중복 확인
        boolean isDuplicated = checkDuplicateNickname(nickname);
        return FindDuplicatedResponse.createFindDuplicatedResponse(isDuplicated);
    }

    public FindDuplicatedResponse findEmail(String email){

        // 1. 중복 확인
        boolean isDuplicated = checkDuplicateEmail(email);
        return FindDuplicatedResponse.createFindDuplicatedResponse(isDuplicated);
    }


    public CodeCheckResponse codeCheck(CodeCheckRequest request) {
        // 1. redis에서 값 가져오기
        String codeExpirationTime = redisService.getEmailExpirationTime(request.getEmail());
        String codeAnswer = redisService.getEmailCode(request.getEmail());

        if (codeExpirationTime == null) {
            throw new CustomException(ErrorCode.CODE_NOT_FOUND);
        }

        // 2. 유효기간 확인
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(LocalDateTime.parse(codeExpirationTime))) {
            throw new CustomException(ErrorCode.CODE_EXPIRED);
        }

        // 3. 코드 일치 여부 확인
        CodeCheckResponse response = CodeCheckResponse.createCodeCheckResponse();
        if (codeAnswer.equals(request.getCode())) {
            response.setIsAvailable();
        }

        return response;
    }

    public void logout(int userId){
        redisService.removeRefreshToken(userId);
    }

    private boolean checkDuplicateEmail(String email){
        Optional<?> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean checkDuplicateNickname(String nickname){
        Optional<?> user = userRepository.findByNickname(nickname);
        return user.isPresent();
    }
}
