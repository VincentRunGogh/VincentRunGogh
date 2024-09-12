package com.vincentrungogh.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; //기본 아이디

    @Column(name = "email")
    private String email; //이메일

    @Column(name = "password")
    private String password; //인코딩된 비밀번호

    @Column(name = "nickname", unique = true, length = 10)
    private String nickname; //닉네임 한글, 영어, 숫자 포함 길이 최대 10글자

    @Column(name = "gender")
    private int gender; //0은 남자, 1은 여자

    @Column(name = "birth")
    private Date birth; //1999-01-01의 형태

    @Column(name = "is_changed")
    private Boolean isChanged; //비밀번호 재발급 여부 default는 false

    @Column(name = "profile")
    private String profile; //프로필 사진 s3 url

    @Column(name = "height")
    private double height; //키

    @Column(name = "weight")
    private double weight; //몸무게

    @Builder
    private User(String email, String password, String nickname, int gender, Date birth, Boolean isChanged, String profile, double height, double weight) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.isChanged = isChanged;
        this.profile = profile;
        this.height = height;
        this.weight = weight;
    }

    public static User createUser(String email, String password, String nickname, int gender, Date birth, Boolean isChanged, String profile, double height, double weight) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .gender(gender)
                .birth(birth)
                .isChanged(false) //비밀번호 재발급 default false
                .profile(null) //추후 기본 프로필로 변경
                .height(height)
                .weight(weight)
                .build();
    }

    //비밀번호 재발급시 사용
    public void updateRandomPassword(String password) {
        this.password = password;
        this.isChanged = true;
    }

    //비밀번호 변경시 사용
    public void updatePassword(String password) {
        this.password = password;
        this.isChanged = false;
    }

}
