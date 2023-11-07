package com.team3.DogCare.PetService.Service;

import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.SaveData;
import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.PetService.Domain.dto.*;
import com.team3.DogCare.PetService.Repository.PetRepository;
import com.team3.DogCare.PetService.Repository.SaveDataRepository;
import com.team3.DogCare.PetService.Repository.VaccineRepository;
import com.team3.DogCare.SignService.Controller.SignException;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private final SaveDataRepository saveDataRepository;

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
            pet.setAge(request.getAge());
            pet.setName(request.getName());
            pet.setWeight(request.getWeight());

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
    public void deleteVaccine(Long vaccineId){vaccineRepository.deleteById(vaccineId);
    }


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

    public String checkEyes(MultipartFile image) throws IOException, URISyntaxException {
        String result;

            // 현재 실행 중인 jar 파일의 경로를 가져옵니다.
        String userHome = "/home/t23203";

        // "SaveData" 폴더를 디렉토리 화면에 생성합니다.
        Path saveDataPath = Paths.get(userHome, "Images");
        if (!Files.exists(saveDataPath)) {
            Files.createDirectories(saveDataPath);
        }

        // 이미지 파일 이름을 수정하여 상대 경로로 저장합니다.
        String modifiedFileName = UUID.randomUUID().toString()+".jpg";

        Path imagePath = saveDataPath.resolve(modifiedFileName);
        Files.write(imagePath, image.getBytes());


       /* String userHome = System.getProperty("user.home");

        // 사용자 홈 디렉토리 아래 바탕화면 경로 생성
        Path desktopPath = Paths.get(userHome, "Desktop");

        // "SaveData" 폴더를 바탕화면에 생성
        Path saveDataPath = desktopPath.resolve("SaveData");
        if (!Files.exists(saveDataPath)) {
            Files.createDirectories(saveDataPath);
        }

        // 이미지 파일 이름을 동적으로 생성
        String uniqueFileName = generateUniqueFileName();
        String fileName = uniqueFileName + ".jpg";
        Path imagePath = saveDataPath.resolve(fileName);
        Files.write(imagePath, image.getBytes());*/

        /*SaveData saveData = SaveData.builder()
                .Age(request.getAge())
                .Breed(request.getBreed())
                .Gender(request.getGender())
                .Identifier(request.getIdentifier())
                //.Disease_name()
                .build();
        saveDataRepository.save(saveData);// 이미지 파일을 SaveData 디렉토리에 저장
        //병명 추가*/

        // 저장한 이미지 파일의 경로를 얻을 수 있습니다.
        String imageFileUrl = imagePath.toString();
        return "이미지 저장 완료";//"반려견의 진단결과는" +result+"입니다.";
    }




}
