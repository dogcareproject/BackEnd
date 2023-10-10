package com.team3.DogCare.SignService.Service;


import com.team3.DogCare.SignService.Controller.SignException;
import com.team3.DogCare.SignService.Domain.Authority;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Domain.dto.SignRequest;
import com.team3.DogCare.SignService.Domain.dto.SignResponse;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import com.team3.DogCare.SignService.Security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtProvider jwtProvider;

    public SignResponse login(SignRequest request) {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new SignException("잘못된 계정 정보입니다."));
        LocalDateTime now = LocalDateTime.now();

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new SignException("잘못된 비밀번호입니다.");
        }
        if (member.getBan() != null && now.isBefore(member.getBan())) {
            throw new SignException(member.getBan() + " 까지 계정이 정지되었습니다.");
        }

        // 정상 로그인 처리
        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .Email(member.getEmail())
                .roles(member.getRoles())
                .token(jwtProvider.createToken(member.getAccount(), member.getRoles()))
                .build();
    }

    public boolean register(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .id(request.getId())
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }





}
