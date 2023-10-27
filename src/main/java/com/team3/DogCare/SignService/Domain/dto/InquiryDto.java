package com.team3.DogCare.SignService.Domain.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InquiryDto {

    private Long inquiryId;
    private String title;
    private String content;
    private LocalDate createTime;
    private Long memberId;
}
