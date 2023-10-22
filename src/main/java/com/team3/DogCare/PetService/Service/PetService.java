package com.team3.DogCare.PetService.Service;

import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.PetService.Domain.dto.FeedRequest;
import com.team3.DogCare.PetService.Domain.dto.PetRequest;
import com.team3.DogCare.PetService.Domain.dto.VaccineRequest;
import com.team3.DogCare.PetService.Domain.dto.WeightDto;
import com.team3.DogCare.PetService.Repository.PetRepository;
import com.team3.DogCare.PetService.Repository.VaccineRepository;
import com.team3.DogCare.SignService.Controller.SignException;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PetRepository petRepository;

    @Autowired
    private final VaccineRepository vaccineRepository;

    public boolean register(PetRequest request) throws Exception{
        Member member = memberRepository.findById(request.getOwnerId()).orElseThrow(()->
                new IllegalArgumentException("Invalid member id: " + request.getOwnerId()));
        try{
            Pet pet = Pet.builder()
                    .petId(request.getPetId())
                    .member(member)
                    .gender(request.getGender())
                    .breed(request.getBreed())
                    .age(request.getAge())
                    .weight(request.getWeight())
                    .name(request.getName())
                    .build();
            petRepository.save(pet);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    return true;
    }

    public boolean infoChange(PetRequest request){
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));
            pet.setBreed(request.getBreed());
            pet.setAge(request.getAge());
            pet.setName(request.getName());
            pet.setWeight(request.getWeight());
            pet.setGender(request.getGender());

        petRepository.save(pet);

        return true;
    }

    public void deletePet(Long petId){

        petRepository.deleteById(petId);
    }
    public void deletePetByMember(Long memberId){
        petRepository.findAllByMemberId(memberId).forEach(p->{
            deletePet(p.getPetId());
        });
    }

    public List<Pet> getPetList(Long ownerId){
        return petRepository.findAllByMemberId(ownerId);
    }

    public String feedPet(FeedRequest request){
        int times; //급여 횟수
        if (request.getAge() < 1){ times = 5;}
        else if (request.getAge() == 1 ){ times = 4;}
        else if (request.getAge() > 1 && request.getAge() < 2 ){ times = 3;}
        else {times = 2;}
        double RER = ((request.getWeight() * 30) + 70);
        double DER = RER * request.getType();
        DecimalFormat change = new DecimalFormat("0.00");
        String result = change.format(DER);
        String feedback = result + " 칼로리를 " + times +"회에 나눠 급여하세요.";
        return feedback;
        //RER = 기초대사량 , DER = 하루 필요 칼로리
        //https://www.fitpetmall.com/blog/dog-food-amount/ type은 해당 주소에서 DER계산식에 따름
    }

    public Boolean addVaccine(VaccineRequest request)throws Exception{
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(()->
                new IllegalArgumentException("Invalid member id: " + request.getPetId()));
        try{
            Vaccine vaccine = Vaccine.builder()
                .vaccineTo(request.getVaccineTo())
                .vaccineFrom(request.getVaccineFrom())
                .vaccineName(request.getVaccineName())
                .pet(pet)
                .vaccineId(request.getVaccineId())
                .vaccineItem(request.getVaccineItem())
                .build();
            vaccineRepository.save(vaccine);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public List<Vaccine> getVaccine(Long petId){
        return vaccineRepository.findAllByPetPetId(petId);

    }
    public void deleteVaccine(Long vaccineId){
        vaccineRepository.deleteById(vaccineId);
    }

    /*public void deleteVaccineByMember(Long memberId) {
        vaccineRepository.findAllByMemberId(memberId).forEach(v->{
            deleteVaccine(v.getVaccineId());
        });
    }*/
    /*public void deleteVaccineByPet(Long petId) {
        vaccineRepository.findAllByPetPetId(petId).forEach(v->{
            deleteVaccine(v.getVaccineId());
        });
    }*/
    public void deleteVaccineByPet(List<Long> petIds) {
        for (Long petId : petIds) {
            vaccineRepository.deleteByPetPetId(petId);
        }
    }

    public String appropriateWeight(WeightDto request){
        double nowWeight = request.getWeight();
        double BCS = request.getBCS();
        double appropriateWeight = nowWeight * (100 /(100 + ((BCS - 5) * 10)));
        DecimalFormat change = new DecimalFormat("0.000");
        String result = change.format(appropriateWeight);

        double needweight = appropriateWeight - nowWeight;
        String needmore = change.format(needweight);

        double loseweight = nowWeight - appropriateWeight;
        String needlose = change.format(loseweight);
        if (nowWeight < appropriateWeight){
            return "반려견의 적정 체중은 " + result + "kg 입니다." + needmore + "kg 만큼 체중 증가가 필요합니다.";
        }
        else if (nowWeight == appropriateWeight){
            return "반려견은 현재 적정 체중 입니다.";
        }
        else {
            return "반려견의 적정 체중은 " + result + "kg 입니다." + needlose + "kg 만큼 체중 감량이 필요합니다.";
        }
    }




}
