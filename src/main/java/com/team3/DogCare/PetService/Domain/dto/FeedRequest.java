package com.team3.DogCare.PetService.Domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedRequest {

    private Long age;

    private Long weight;

    private Long feedCal; //사료 전체 칼로리

    private Long feedWeight; // 사료 무게

    private Long type; //타입 (성견인지, 과체중 성견인지, 중성화or 비중성화인지)
}
