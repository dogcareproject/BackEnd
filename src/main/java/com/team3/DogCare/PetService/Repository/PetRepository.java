package com.team3.DogCare.PetService.Repository;


import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.SignService.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByOwnerId(String owner_id);
    Optional<Pet> findByOwnerIdAndName(String owner_id, String name);


}
