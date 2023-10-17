package com.team3.DogCare.PetService.Controller;


import com.team3.DogCare.PetService.Domain.dto.FeedRequest;
import com.team3.DogCare.PetService.Domain.dto.PetRequest;
import com.team3.DogCare.PetService.Service.PetService;
import com.team3.DogCare.SignService.Controller.SignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Controller
@RequiredArgsConstructor
public class PetController {


    @Autowired
    private final PetService petService;


    @PostMapping(value = "user/petRegister")
    public ResponseEntity<Boolean> addPet(@RequestBody PetRequest request) throws Exception {
        return new ResponseEntity<>(petService.register(request), HttpStatus.OK);
    }//받는 인자 : name, weight, gender, age, breed, ownerId;


    @PutMapping(value = "user/petInfoChange")
    public ResponseEntity<?> infochange(@RequestBody PetRequest request){
        try{
            return new ResponseEntity<>(petService.infoChange(request),HttpStatus.OK);
        }catch(SignException e) {
            String errorMessage = e.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }//받는 인자 : name, weight, gender, age, breed
    }
    @PostMapping(value = "user/petFeed")
    public ResponseEntity<String> addPet(@RequestBody FeedRequest request) throws Exception {
        return new ResponseEntity<>(petService.feedPet(request), HttpStatus.OK);
    }//받는 인자 : age, weight , type




}
