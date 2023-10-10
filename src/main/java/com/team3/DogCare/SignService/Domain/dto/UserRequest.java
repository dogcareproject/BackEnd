package com.team3.DogCare.SignService.Domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest{

    private Long Id;

    private String account;

    private String password;

    private String Email;

}