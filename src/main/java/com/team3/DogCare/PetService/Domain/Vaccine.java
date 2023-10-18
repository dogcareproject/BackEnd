package com.team3.DogCare.PetService.Domain;


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
    private Long memberId;

    @Column
    private String petName;

    @Column
    private String vaccineName;

    @Column
    private LocalDate vaccineFrom;

    @Column
    private LocalDate vaccineTo;
}
