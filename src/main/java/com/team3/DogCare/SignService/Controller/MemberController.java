package com.team3.DogCare.SignService.Controller;

import com.team3.DogCare.SignService.Domain.Inquiry;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Domain.dto.*;
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
    @PutMapping("/user/infoChange")
    public ResponseEntity<?> info_change(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.infoChange(request), HttpStatus.OK);
        }catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 : email, password, name,id (id로 계정 확인 후, 받은 email과 password로 계정 정보 변경)
        //기본적으로 회원정보 변경시 로그인한 유저의 정보가 미리 기입되어 있게 할것.

    }
    @PostMapping("/Find/Account")
    public ResponseEntity<?> findid(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.FindAccount(request), HttpStatus.OK);
        }catch (SignException e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 : email (이메일로 계정 리턴)

    }
    @PostMapping("/Find/Pwd")
    public ResponseEntity<?> Findpwd(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(emailService.sendPwdEmail(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 : account, email (이메일과 계정으로 id를 찾아서 해당 계정의 이메일로 임시 비밀번호로 변경후
        //임시 비밀번호 보냄.
    }
    @PostMapping("/user/withdrawal")
    public ResponseEntity<?> deleteMember(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.withdrawal(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 :id, account , password (비밀번호 입력받아서 최종확인 후 계정삭제)

    }
    @PostMapping("/admin/withdrawal")
    public ResponseEntity<?> forced_deleteMember(@RequestBody BanDto request){
        try{
            return new ResponseEntity<>(memberService.foredwithdrawal(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 : id / id를 입력받아 계정 삭제

    }
    @PostMapping("/admin/banMember")
    public ResponseEntity<?> memberban(@RequestBody BanDto request){
        try{
            return new ResponseEntity<>(memberService.accountBan(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } //받는 인자 : id , bantime
    }
    @GetMapping("/getMemberList")
    public ResponseEntity<List<Member>> getMembers() {
        return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    }

    @GetMapping("/admin/getMemberInquiries")
    public ResponseEntity<List<Inquiry>> getMemberInquirys(@RequestParam Long memberId) {
        return new ResponseEntity<>(memberService.getMemberInquiries(memberId), HttpStatus.OK);
    }//받는 인자 : memberId;
    // /getMemberInquirys?memberId=123 형식으로 받을것.

    @GetMapping("/admin/getInquiries")
    public ResponseEntity<List<Inquiry>> getInquirys() {
        return new ResponseEntity<>(memberService.getInquiries(), HttpStatus.OK);
    }
    @PostMapping("/admin/deleteInquiries")
    public ResponseEntity<?> deleteInquirys(@RequestBody InquiryDto request){
        memberService.deleteInquiry(request.getInquiryId());
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/user/inquiry")
    public ResponseEntity<?> inquiry(@RequestBody InquiryDto request){
        try{
            return new ResponseEntity<>(memberService.inquiry(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } //받는 인자 : Long memberid, String content, String title, LocalDate createTime
    }

    @PostMapping("/admin/inquiryAnswer")
    public ResponseEntity<?> inquiryAnswer(@RequestBody EmailDto request){
        try{
            return new ResponseEntity<>(emailService.sendInquiryEmail(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } //받는 인자 : Long memberid, String content, String title, LocalDate createTime
    }



}
