package com.team3.DogCare.PetService.Service;

import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.dto.FeedRequest;
import com.team3.DogCare.PetService.Domain.dto.PetRequest;
import com.team3.DogCare.PetService.Repository.PetRepository;
import com.team3.DogCare.SignService.Controller.SignException;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PetRepository petRepository;

    public static boolean register(PetRequest request) throws Exception{
        try{
            Pet pet = Pet.builder()
                    .id(request.getId())
                    .ownerId(request.getOwnerId())
                    .gender(request.getGender())
                    .breed(request.getBreed())
                    .age(request.getAge())
                    .weight(request.getWeight())
                    .name(request.getName())
                    .build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    return true;
    }

    public boolean infoChange(PetRequest request){
        Pet pet = petRepository.findById(request.getId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));
            pet.setBreed(request.getBreed());
            pet.setAge(request.getAge());
            pet.setName(request.getName());
            pet.setWeight(request.getWeight());
            pet.setGender(request.getGender());

        petRepository.save(pet);

        return true;
    }

    public void deletePet(PetRequest request) throws Exception {

        petRepository.deleteById(request.getId());
    }

    public List<Pet> getPetList(Long Ownerid){
        return petRepository.findAllByOwnerId(Ownerid);
    }

    public String feedPet(FeedRequest request){
        int times; //급여 횟수
        if (request.getAge() < 1){ times = 5;}
        else if (request.getAge() == 1 ){ times = 4;}
        else if (request.getAge() > 1 && request.getAge() < 2 ){ times = 3;}
        else {times = 2;}
        Long RER = ((request.getWeight() * 30) + 70);
        Long DER = RER * request.getType();
        String feedback = DER + "칼로리 만큼을 " + times +"회에 나눠 급여하세요.";
        return feedback;
    }




}
