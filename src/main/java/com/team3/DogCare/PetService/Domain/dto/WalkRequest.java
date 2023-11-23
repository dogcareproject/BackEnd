package com.team3.DogCare.PetService.Domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WalkRequest {

    private Long walkId;

    private Double walkDistance;

    private LocalDate walkDate;

    private Long petId;

    private Double walkTime;

    private String endTime;
}
