package com.team3.DogCare.PetService.Domain.dto;


import com.team3.DogCare.SignService.Domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class VaccineRequest {
    private Long vaccineId;

    //private Long memberId; //member

    private Long petId; //pet

    private String vaccineName;

    private String vaccineItem;

    private LocalDate vaccineFrom;

    private LocalDate vaccineTo;

}
