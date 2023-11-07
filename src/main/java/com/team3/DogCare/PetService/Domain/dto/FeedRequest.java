package com.team3.DogCare.PetService.Domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedRequest {

    private Long petId;

    private String feed;

    private String needKcal;
}
