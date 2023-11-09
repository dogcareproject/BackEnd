package com.team3.DogCare.PetService.Repository;


import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.PetService.Domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkRepository extends JpaRepository <Walk,Long> {
    List<Walk> findByPetMemberId(Long memberId);
    List<Walk> findAllByPetPetId(Long petId);
}
