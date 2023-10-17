package com.team3.DogCare.PetService.Domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedResponse {

    private int RER; //기초대사량

    private int DER; //필요 에너지량
}
