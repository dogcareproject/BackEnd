package com.team3.DogCare.PetService.Repository;


import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.SignService.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByOwnerId(String ownerId);
    Optional<Pet> findByOwnerIdAndName(String ownerId, String name);
    List<Pet> findAllByOwnerId(Long ownerId);
    void deleteAllByOwnerId(Long ownerId);


}
