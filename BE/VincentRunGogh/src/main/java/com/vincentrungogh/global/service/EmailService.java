package com.vincentrungogh.global.service;

import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisService redisService;

    @Value("${spring.mail.username}")
    private String serviceName;

    // 랜덤 인증번호 생성
    private int makeRandomCode(){
        Random random = new Random();
        String randomNumber = "";

        for (int i = 0; i < 6; i++) {
            randomNumber += random.nextInt(10);
        }
        return Integer.parseInt(randomNumber);
    }

    // 이메일 전송
    public void sendEmail(String to){
        // 1. 인증 코드 발급
        int code = makeRandomCode();

        // 2. 이메일 작성
        String from = serviceName;
        String title = "회원 가입을 위한 이메일입니다!";
        String content =
                "이메일을 인증하기 위한 절차입니다." +
                        "<br><br>" +
                        "인증 번호는 " + code + "입니다.";

        // 3. 이메일 전송 세팅
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (Exception e){
            log.info("이메일 전송 실패" + e.getMessage());
            throw new CustomException(ErrorCode.FAILED_SEND_EMAIL);
        }

        // redis에 5분 동안 이메일과 인증 코드 저장
        redisService.saveEmailCode(to, String.valueOf(code));
    }
}
