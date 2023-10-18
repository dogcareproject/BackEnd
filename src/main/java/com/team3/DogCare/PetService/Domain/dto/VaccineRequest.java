package com.team3.DogCare.PetService.Domain.dto;


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

    private Long memberId;

    private String VaccineName;

    private LocalDate VaccineFrom;

    private LocalDate VaccineTo;

    private String petName;
}
