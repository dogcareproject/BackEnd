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

    private String email;

    private String password;

    private List<Authority> roles = new ArrayList<>();

    private String token;

    private LocalDateTime ban;

    public SignResponse(Member member){
        this.id = member.getId();
        this.account = member.getAccount();
        this.name = member.getName();
        this.email = member.getEmail();
        this.ban = member.getBan();
        this.roles = getRoles();
        this.password = getPassword();
    }
}

