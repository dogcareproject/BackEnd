package com.team3.DogCare.PetService.Domain;


import jakarta.persistence.*;
import lombok.*;
import com.team3.DogCare.SignService.Domain.Member;

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
    private Long petId;


    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

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
