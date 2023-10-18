package com.team3.DogCare.PetService.Controller;


import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.PetService.Domain.dto.FeedRequest;
import com.team3.DogCare.PetService.Domain.dto.PetRequest;
import com.team3.DogCare.PetService.Domain.dto.VaccineRequest;
import com.team3.DogCare.PetService.Service.PetService;
import com.team3.DogCare.SignService.Controller.SignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/pet")
public class PetController {


    @Autowired
    private final PetService petService;


    @PostMapping(value = "/Register")
    public ResponseEntity<Boolean> addPet(@RequestBody PetRequest request) throws Exception {
        return new ResponseEntity<>(petService.register(request), HttpStatus.OK);
    }//받는 인자 : name, weight, gender, age, breed, ownerId;

    @DeleteMapping(value = "/deletePet")
    public ResponseEntity<String> deletePet(@RequestParam Long petId) {
        petService.deletePet(petId);
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    @PutMapping(value = "/InfoChange")
    public ResponseEntity<?> infochange(@RequestBody PetRequest request){
        try{
            return new ResponseEntity<>(petService.infoChange(request),HttpStatus.OK);
        }catch(SignException e) {
            String errorMessage = e.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 : name, weight, gender, age, breed
    }
    @GetMapping(value = "/pets")
    public List<Pet> getPetList(@RequestParam Long memberId) {
        return petService.getPetList(memberId);
    }
    @PostMapping(value = "/Feed")
    public ResponseEntity<String> addPet(@RequestBody FeedRequest request) {
        return new ResponseEntity<>(petService.feedPet(request), HttpStatus.OK);
    }//받는 인자 : age, weight , type

    @PostMapping(value = "/addVaccine")
    public ResponseEntity<VaccineRequest> addVaccine(@RequestBody VaccineRequest request) {
        return new ResponseEntity<>(petService.addVaccine(request), HttpStatus.OK);
    }//받는 인자 : petName, memberId, vaccineName, vaccineFrom, vaccineTo

    @GetMapping(value = "/vaccines")
    public List<Vaccine> addVaccine(@RequestParam Long memberId) {
        return petService.getVaccine(memberId);
    }//받는 인자 : memberId, 주소는 user/pet/vaccines?memberId=123 형식으로

    @DeleteMapping(value = "/deleteVaccine")
    public ResponseEntity<String> deleteVaccine(@RequestParam Long vaccineId) {
        petService.deleteVaccine(vaccineId);
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }




}
