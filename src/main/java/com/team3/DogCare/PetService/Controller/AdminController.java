package com.team3.DogCare.PetService.Controller;

import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final PetService petService;

    @GetMapping(value = "/pets")
    public List<Pet> getPetList() {
        return petService.getPets();}

}
