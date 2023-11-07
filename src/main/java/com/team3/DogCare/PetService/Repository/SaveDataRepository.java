package com.team3.DogCare.PetService.Repository;

import com.team3.DogCare.PetService.Domain.SaveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveDataRepository extends JpaRepository<SaveData,Long> {

}
