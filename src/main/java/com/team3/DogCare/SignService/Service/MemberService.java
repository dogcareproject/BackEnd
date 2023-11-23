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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.*;
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


    private static final Logger logger = Logger.getLogger(MemberService.class.getName());

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

    public boolean infoChange(UserRequest request) {
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setEmail(request.getEmail());
        member.setName(request.getName());

        memberRepository.save(member);

        return true;
    }

    public boolean pwdChange(UserRequest request) {
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);

        return true;
    }

    public String FindAccount(UserRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new SignException("이메일을 찾을수 없습니다"));

        return member.getAccount();
    }

    public Boolean withdrawal(UserRequest request) throws Exception {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));
        List<Pet> pet = petRepository.findAllByMemberId(request.getId());
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
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

    public List<Inquiry> getInquiries() {
        return inquiryRepository.findAll();
    }


    public List<Inquiry> getMemberInquiries(Long memberId) {
        return inquiryRepository.findAllByMemberId(memberId);
    }

    public void deleteInquiry(Long InquiryId) {
        inquiryRepository.deleteById(InquiryId);
    }

    public void deleteInquiriesByMember(Long memberId) {
        inquiryRepository.findAllByMemberId(memberId).forEach(i -> {
            deleteInquiry(i.getInquiryId());
        });
    }
    @Scheduled(cron = "0 0 0/6 * * *")
    public void backupDatabase() {
        try {
            setupLogger();  // 이 부분을 백업 작업 전에 한 번만 호출

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String backupFileName = "backup_" + dateFormat.format(new Date()) + ".sql";
            String username = "dbid232";
            String password = "dbpass232";
            String databaseName = "db23203";
            String userHome = System.getProperty("user.home");

            String executeCmd = "mysqldump -u" + username + " -p" + password +
                    " " + databaseName + " > " + userHome + "/BackUp/" + backupFileName;

            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", executeCmd);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                String successMessage = "Database backup successful. File: " + backupFileName;
                System.out.println(successMessage);
                logger.log(Level.INFO, successMessage);
            } else {
                String errorMessage = "Database backup failed. Exit code: " + exitCode;
                System.out.println(errorMessage);
                logger.log(Level.SEVERE, errorMessage);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Exception during database backup", e);
        }
    }

    private void setupLogger() {
        try {
            // 이미 핸들러가 추가되어 있는지 확인
            boolean handlerExists = false;
            for (Handler handler : logger.getHandlers()) {
                if (handler instanceof FileHandler) {
                    handlerExists = true;
                    break;
                }
            }

            // 핸들러가 추가되어 있지 않다면 새로운 핸들러 추가
            if (!handlerExists) {
                FileHandler fileHandler = new FileHandler("/home/t23203/BackUpLog/backup.log", 0, 1, true);
                SimpleFormatter formatter = new SimpleFormatter() {
                    @Override
                    public synchronized String format(LogRecord lr) {
                        // 날짜 포맷 변경
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = dateFormat.format(new Date(lr.getMillis()));

                        // [yyyy-MM-dd HH:mm:ss] 형식으로 로그 메시지 포맷팅
                        return String.format("[%s] %s%n", formattedDate, lr.getMessage());
                    }
                };
                fileHandler.setFormatter(formatter);
                logger.addHandler(fileHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> readLogFileContent() throws IOException {
        // 로그 파일 읽기
        Path path = Paths.get("/home/t23203/BackUpLog/backup.log");
        return Files.lines(path).collect(Collectors.toList());
    }



}
