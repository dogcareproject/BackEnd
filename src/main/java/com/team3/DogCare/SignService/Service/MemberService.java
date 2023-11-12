package com.team3.DogCare.SignService.Service;


import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Repository.PetRepository;
import com.team3.DogCare.PetService.Repository.VaccineRepository;
import com.team3.DogCare.PetService.Service.PetService;
import com.team3.DogCare.SignService.Controller.SignException;
import com.team3.DogCare.SignService.Domain.Authority;
import com.team3.DogCare.SignService.Domain.Inquiry;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Domain.dto.*;
import com.team3.DogCare.SignService.Repository.InquiryRepository;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import com.team3.DogCare.SignService.Security.JwtProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final VaccineRepository vaccineRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtProvider jwtProvider;
    @Autowired
    private final PetService petService;
    @Autowired
    private InquiryRepository inquiryRepository;


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
                .email(member.getEmail())
                .roles(member.getRoles())
                .password(member.getPassword())
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
    public boolean adminRegister(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .id(request.getId())
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_ADMIN").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;

    }
    public boolean infoChange(UserRequest request){
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setEmail(request.getEmail());
        member.setName(request.getName());

        memberRepository.save(member);

        return true;
    }
    public String FindAccount(UserRequest request){
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new SignException("이메일을 찾을수 없습니다"));

        return member.getAccount();
    }
    public Boolean withdrawal(UserRequest request) throws Exception {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));
        List<Pet> pet = petRepository.findAllByMemberId(request.getId());
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            return false;
        }

        Long memberId = request.getId();
        List<Long> petId = pet.stream()
                .map(Pet::getPetId) // 각 Pet 엔티티에서 petId를 추출
                .collect(Collectors.toList());

        petService.deleteWalkByPet(petId);
        petService.deleteVaccineByPet(petId);
        petService.deletePetByMember(memberId);
        deleteInquiriesByMember(memberId);
        memberRepository.deleteById(member.getId());


        return true;
    }
    public Boolean foredwithdrawal(BanDto request) throws Exception {
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));
        List<Pet> pet = petRepository.findAllByMemberId(request.getId());
        Long memberId = request.getId();
        List<Long> petId = pet.stream()
                .map(Pet::getPetId)
                .collect(Collectors.toList());

        petService.deleteWalkByPet(petId);
        petService.deleteVaccineByPet(petId);
        petService.deletePetByMember(memberId);
        deleteInquiriesByMember(memberId);
        memberRepository.deleteById(member.getId());

        return true;
    }
    public Boolean accountBan(BanDto request) throws Exception {
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));

        LocalDateTime banned = LocalDateTime.now().plusDays(request.getBantime());
        member.setBan(banned);
        memberRepository.save(member);
        return true;
    }
    public List<Member> getMemberList() {

        return memberRepository.findAll();
    }
    public Boolean inquiry(InquiryDto request) throws Exception {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));

        Inquiry inquiry = Inquiry.builder()
                .inquiryId(request.getInquiryId())
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .createTime(request.getCreateTime())
                .build();
        inquiryRepository.save(inquiry);

        return true;
    }

    public List<Inquiry> getInquiries(){
        return inquiryRepository.findAll();
    }


    public List<Inquiry> getMemberInquiries(Long memberId){
        return inquiryRepository.findAllByMemberId(memberId);
    }

    public void deleteInquiry(Long InquiryId){
        inquiryRepository.deleteById(InquiryId);
    }

    public void deleteInquiriesByMember(Long memberId){
        inquiryRepository.findAllByMemberId(memberId).forEach(i ->{
            deleteInquiry(i.getInquiryId());
        });
    }

}
