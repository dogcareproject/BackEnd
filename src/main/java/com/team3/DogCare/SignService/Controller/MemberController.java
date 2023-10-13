package com.team3.DogCare.SignService.Controller;

import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Domain.dto.BanDto;
import com.team3.DogCare.SignService.Domain.dto.SignRequest;
import com.team3.DogCare.SignService.Domain.dto.SignResponse;
import com.team3.DogCare.SignService.Domain.dto.UserRequest;
import com.team3.DogCare.SignService.Service.EmailService;
import com.team3.DogCare.SignService.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;
    @Autowired
    private final EmailService emailService;

    @CrossOrigin("*")
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

    @CrossOrigin("*")
    @PostMapping(value = "/adminRegister")
    public ResponseEntity<Boolean> adminsignup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.adminRegister(request), HttpStatus.OK);
    }
    @PutMapping("/User/infoChange")
    public ResponseEntity<?> info_change(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.infoChange(request), HttpStatus.OK);
        }catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

    }
    @PostMapping("/User/FindAccount")
    public ResponseEntity<?> findid(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.FindAccount(request), HttpStatus.OK);
        }catch (SignException e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

    }
    @PostMapping("/User/FindPWD")
    public ResponseEntity<?> Findpwd(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(emailService.sendPwdEmail(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }
    @PostMapping("/User/withdrawal")
    public ResponseEntity<?> deleteMember(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.withdrawal(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

    }
    @PostMapping("/admin/banMember")
    public ResponseEntity<?> memberban(@RequestBody BanDto request){
        try{
            return new ResponseEntity<>(memberService.accountBan(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }
    @GetMapping("/getMemberList")
    public ResponseEntity<List<Member>> getMembers() {
        return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    }

}
