package com.team3.DogCare.PetService.Repository;


import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.PetService.Domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkRepository extends JpaRepository <Walk,Long> {
    List<Walk> findByPetMemberId(Long memberId);
    List<Walk> findAllByPetPetId(Long petId);
    void deleteByPetPetId(Long petId);

    @Query("SELECT w.pet.breed, AVG(w.walkTime), AVG(w.walkDistance) " +
            "FROM Walk w " +
            "GROUP BY w.pet.breed")
    List<Object[]> findAverageWalkInfoByBreed();

    @Query("SELECT AVG(w.walkTime), AVG(w.walkDistance) FROM Walk w")
    List<Object[]> findAverageWalk(); //전체 평균 산책시간, 거리
}
