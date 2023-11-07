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
public class SaveData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SaveDataId")
    private Long SaveDataId;

    @Column
    private String Url; //image

    @Column
    private String filename;

    @Column
    private String Breed;

    @Column
    private String Gender;

    @Column
    private String Age;

    @Column
    private String Disease_name;

    @Column
    private String Identifier;

}
