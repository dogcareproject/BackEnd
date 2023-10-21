package com.team3.DogCare.PetService.Domain;


import com.team3.DogCare.SignService.Domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Inheritance
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long vaccineId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column
    private String petName;

    @Column
    private String vaccineName;

    @Column
    private LocalDate vaccineFrom;

    @Column
    private LocalDate vaccineTo;
}
