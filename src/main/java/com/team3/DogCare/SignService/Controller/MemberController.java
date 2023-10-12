package com.team3.DogCare.SignService.Controller;

import com.team3.DogCare.SignService.Domain.dto.SignRequest;
import com.team3.DogCare.SignService.Domain.dto.SignResponse;
import com.team3.DogCare.SignService.Service.EmailService;
import com.team3.DogCare.SignService.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final EmailService emailService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> signin(@RequestBody SignRequest request) {
        try {
            SignResponse response = memberService.login(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "로그인 중에 오류가 발생했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
    }

    @PostMapping(value = "/adminRegister")
    public ResponseEntity<Boolean> adminsignup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.adminRegister(request), HttpStatus.OK);
    }
}
