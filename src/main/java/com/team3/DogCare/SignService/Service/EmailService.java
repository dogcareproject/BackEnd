package com.team3.DogCare.SignService.Service;

import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Domain.dto.EmailDto;
import com.team3.DogCare.SignService.Domain.dto.UserRequest;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private JavaMailSender emailSender;
    private static final String hostSMTPid = "naxc14@gmail.com";

    public String createTempPwd() { //임시 비밀번호 생성
        int from = 48; // 0
        int to = 122; // z
        int targetStringLength = 10;
        String pwd;
        Random random = new Random();
        pwd = random.ints(from, to + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return pwd;
    }

    public String tempPwd(UserRequest request){
        Member member = (Member) memberRepository.findByEmailAndAccount(request.getEmail(),request.getAccount()).orElseThrow(() ->
                new IllegalArgumentException("이메일 또는 계정을 찾을수 없습니다."));
        String tempPwd = createTempPwd();
        member.setPassword(passwordEncoder.encode(tempPwd));
        memberRepository.save(member);
        return tempPwd;
    }

    public Boolean sendPwdEmail(UserRequest request) throws Exception {

        String tempPwd = tempPwd(request);
        EmailDto email = new EmailDto();
        email.setAddress(request.getEmail());
        email.setTitle(request.getAccount()+"님의 멍멍케어 임시비밀번호 안내입니다.");
        email.setMessage("임시 비밀번호는 "+ tempPwd +" 입니다.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getAddress());
        message.setFrom(hostSMTPid);
        message.setSubject(email.getTitle());
        message.setText(email.getMessage());

        emailSender.send(message);

        return true;
    }

    public Boolean sendInquiryEmail(EmailDto response) throws Exception {
        Member member = memberRepository.findById(response.getMemberId()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));
        EmailDto email = new EmailDto();
        email.setAddress(member.getEmail());
        email.setTitle(member.getName()+"님의 멍멍케어 문의 답변입니다.");
        email.setMessage(response.getMessage());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getAddress());
        message.setFrom(hostSMTPid);
        message.setSubject(email.getTitle());
        message.setText(email.getMessage());

        emailSender.send(message);

        return true;
    }

}