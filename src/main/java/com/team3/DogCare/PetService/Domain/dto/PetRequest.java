package com.team3.DogCare.PetService.Domain.dto;

import com.team3.DogCare.SignService.Domain.Member;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequest {

    private Long petId;

    private Long ownerId; //member

    private String name;

    private String gender;

    private String breed;

    private String age;

    private Double weight;
}
