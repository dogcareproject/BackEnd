package com.team3.DogCare.PetService.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Walk {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walkID;

    @JoinColumn(name = "petId")
    @ManyToOne
    private Pet pet;

    @Column
    private Double walkDistance;

    @Column
    private LocalDate walkDate;


}
