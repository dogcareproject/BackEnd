package com.team3.DogCare.SignService.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long inquiryId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column
    private String content;

    @Column
    private LocalDate createTime;

    @Column
    private String title;
}
