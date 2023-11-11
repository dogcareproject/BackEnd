package com.team3.DogCare.PetService.Repository;


import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.SignService.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByMemberId(Long ownerId);
    Optional<Pet> findByMemberIdAndName(String ownerId, String name);
    List<Pet> findAllByMemberId(Long ownerId);

    @Query("SELECT p.breed, MAX(p.weight), AVG(p.weight), MIN(p.weight) " +
            "FROM Pet p " +
            "GROUP BY p.breed")
    List<Object[]> findAverageWeightByBreed();

    /*@Query(value = "SELECT breed, AVG(weight) FROM pet GROUP BY breed", nativeQuery = true)
    List<Object[]> findAverageWeightByBreed();*/



}
