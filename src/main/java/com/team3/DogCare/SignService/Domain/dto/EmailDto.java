package com.team3.DogCare.SignService.Domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter @Setter @NoArgsConstructor
public class EmailDto {

    private String address;
    private String title;
    private String message;
}
