package com.team3.DogCare.PetService.Domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Inheritance
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petId")
    private Long id;

    @Column
    @JoinColumn(name = "memberId")
    @ManyToOne
    private Long owner_id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private String breed;

    @Column
    private String age;

    @Column
    private String weight;
}
