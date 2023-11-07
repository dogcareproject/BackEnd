package com.team3.DogCare.PetService.Domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class checkResponse {

    private String disease;

    private String info;
}
