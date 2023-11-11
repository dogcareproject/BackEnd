package com.team3.DogCare.PetService.Domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CheckRequest {



    private String gender;

    private String breed;

    private String age;

    private Double weight;

    private String identifier;


}
