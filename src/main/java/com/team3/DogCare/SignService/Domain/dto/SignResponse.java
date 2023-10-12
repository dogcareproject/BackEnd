package com.team3.DogCare.SignService.Domain.dto;


import com.team3.DogCare.SignService.Domain.Authority;
import com.team3.DogCare.SignService.Domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class SignResponse {

    private Long id;

    private String account;

    private String name;

    private String Email;

    private String Doctor_check;

    private List<Authority> roles = new ArrayList<>();

    private String token;

    private LocalDateTime ban;

    public SignResponse(Member member){
        this.id = member.getId();
        this.account = member.getAccount();
        this.name = member.getName();
        this.Email = member.getEmail();
        this.Doctor_check = member.getDoctor_check();
        this.ban = member.getBan();
        this.roles = getRoles();
    }
}

