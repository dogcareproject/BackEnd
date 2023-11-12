package com.team3.DogCare.PetService.Service;

import com.team3.DogCare.PetService.Domain.Pet;
import com.team3.DogCare.PetService.Domain.Vaccine;
import com.team3.DogCare.PetService.Domain.Walk;
import com.team3.DogCare.PetService.Domain.dto.*;
import com.team3.DogCare.PetService.Repository.PetRepository;
import com.team3.DogCare.PetService.Repository.SaveDataRepository;
import com.team3.DogCare.PetService.Repository.VaccineRepository;
import com.team3.DogCare.PetService.Repository.WalkRepository;
import com.team3.DogCare.SignService.Controller.SignException;
import com.team3.DogCare.SignService.Domain.Member;
import com.team3.DogCare.SignService.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    @Autowired
    private final WalkRepository walkRepository;

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
        List<Walk> walks = walkRepository.findAllByPetPetId(petId);
        for (Walk walk : walks){
            walkRepository.delete(walk);
        }
        List<Vaccine> vaccines = vaccineRepository.findAllByPetPetId(petId);
        for (Vaccine vaccine : vaccines){
            vaccineRepository.delete(vaccine);
        }
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

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public Boolean feedPet(FeedRequest request){
    Pet pet = petRepository.findById(request.getPetId()).orElseThrow(()->
            new IllegalArgumentException("Invalid pet id: " + request.getPetId()));
    pet.setFeed(request.getFeed());
    pet.setNeedKcal(request.getNeedKcal());
    petRepository.save(pet);

    return true;
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

    public void deleteWalkByPet(List<Long> petIds) {
        for (Long petId : petIds) {
            walkRepository.deleteByPetPetId(petId);
        }
    }


    public Boolean appropriateWeight(WeightDto request){
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(()->
                new IllegalArgumentException("Invalid member id: " + request.getPetId()));
        pet.setApproxyWeight(request.getWeight());
        petRepository.save(pet);

        return true;
    }

    public checkResponse checkSkin(MultipartFile image) throws IOException, URISyntaxException {

        String userHome = "/home/t23203";
        //String userHome = System.getProperty("user.home");
        Path saveDataPath = Paths.get(userHome, "SkinImages");
        if (!Files.exists(saveDataPath)) {
            Files.createDirectories(saveDataPath);
        }

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

        // 저장한 이미지 파일의 경로를 얻을 수 있습니다

        String imageFileUrl = imagePath.toString();

        int code = 1;
        String Disease = "";
        String info = "";
        if (code == 1 ){
            Disease = "구진, 플라크";
            info = "구진이란 염증성 여드름 병변과 비염증성 여드름 병변의 중간 " +
                    "형태이며 피부의 단단한 덩어리로 직경이 0.5cm~1cm를 구진, 그 이상을 플라크라 합니다." +
                    "작고 딱딱한 붉은 색의 병변으로 안에 고름은 잡히지 않은 상태로 나타납니다.";

        }else if(code == 2){
            Disease = "비듬,각질,상피성잔고리";
            info = "비듬, 각질은 정상적인 피부활동으로 생기는 피부 막이 떨어져 나가는 현상입니다."+
                    "상피성잔고리는 농포나 수포가 파열한 후 나타나는 원형의 비듬입니다." +
                    "피부병의 증상일 수 있으나, 주변 환경이나 스트레스에 의해 발생하기도 합니다.";

        }else if(code == 3){
            Disease = "과다색소침착, 태선화";
            info = "과다색소침착은 강아지의 피부나 모발에서 색소의 과다 침전을 나타내며, " +
                    "이로 인해 해당 부위가 어두워지거나 색이 짙어질 수 있습니다." +
                    "태선화는 피부가 두꺼워지고 단단하며, 거칠게 변합니다. 지속적인 자극이나 피부병으로 인해 발생할 수 있습니다.";
        }else if(code == 4){
            Disease = "농포,여드름";
            info = "농포와 여드름 모두 피부에 생긴 작고 뾰족한 형태의 발진입니다."+
                    "여드름의 경우 피지선의 과도한 분비나 기름진 털에 의해 발생할 수 있습니다."+
                    "농포의 경우 염증 반응이 있거나 피부에 세균 감염이 있을 경우 발생할 수 있습니다.";
        }else if(code ==5){
            Disease = "미란,궤양";
            info = "상처로 인해 표피가 떨어져 나가는 경우, 이 범위가 작고 표피에 한정되는 것을 미란이라 합니다."+
                    "하지만 그 상처가 깊어 표피 이하까지 범위가 미치고, 탈락이 발생하는 경우 궤양이라 하고 흉터가 동반됩니다.";

        }else if(code == 6){
            Disease = "결절,종괴";
            info = "결절과 종괴 모두 피부에서 생기는 혹을 말합니다. 이때 그 크기가 크면 종괴로, 작으면 결절이라 합니다."+
                    "둘 모두 외부 상처나 감염으로 인한 염증반응으로 나타날 수 있습니다.";
        }else if(code == 7 ){
            Disease = "감염성피부염";
            info = "피부에 발생하는 감염을 동반한 피부염을 나타냅니다. 이러한 감염은 피부에 있는 세균, 바이러스, 진드기 또는 기타 병원체에 의해 유발됩니다. " +
                    "강아지 피부염은 다양한 형태와 원인을 가질 수 있으며, 일반적으로 감염성 피부염은 " +
                    "피부의 염증 반응을 동반하고 피부 장애를 유발하는 감염에 의해 발생합니다.";
        }

        return checkResponse.builder().disease(Disease).info(info).build();
    }

    public checkResponse checkEyes(MultipartFile image) throws IOException, URISyntaxException {

        //String userHome = "/home/t23203";
        String userHome = System.getProperty("user.home");
        Path saveDataPath = Paths.get(userHome, "EyeImages");
        if (!Files.exists(saveDataPath)) {
            Files.createDirectories(saveDataPath);
        }

        String modifiedFileName = UUID.randomUUID().toString()+".jpg";

        Path imagePath = saveDataPath.resolve(modifiedFileName);
        Files.write(imagePath, image.getBytes());

        /*SaveData saveData = SaveData.builder()
                .Age(request.getAge())
                .Breed(request.getBreed())
                .Gender(request.getGender())
                .Identifier(request.getIdentifier())
                //.Disease_name()
                .build();
        saveDataRepository.save(saveData);// 이미지 파일을 SaveData 디렉토리에 저장
        //병명 추가*/

        // 저장한 이미지 파일의 경로를 얻을 수 있습니다

        String imageFileUrl = imagePath.toString();

        int code = 1;
        String Disease = "";
        String info = "";
        if (code == 1 ){
            Disease = "안검염";
            info = "안검염이란 눈꺼풀의 피부와 속눈썹 부위 및 부속선에 염증이 생긴것을 말합니다." +
                    "눈 부속기관인 안검의 염증은 눈병의 하나로 분류되며," +
                    "결막염, 각막염 등과 함께 많이 발생하는 질병 중 하나입니다.";

        }else if(code == 2){
            Disease = "안검종양";
            info = "안검종양은 개의 눈 주위에 발생하는 종양을 가리키는 용어입니다. " +
                    "안검종양은 다양한 형태와 크기로 나타날 수 있습니다."+
                    "대부분 검판선의 분비선에서 유래하게 되는데 크기가 커지면 " +
                    "안구를 압박하거나 결막이나 각막을 만성적으로 자극하여 염증을 유발할 수 있습니다.";
        }else if(code == 3){
            Disease = "안검내반증";
            info = "개의 눈꺼풀 가장자리가 눈으로 말려 들어가 피모가 항상 닿아 있어 이로 인해 눈물 흘림과 눈 비빔, " +
                    "깜박임 등 다양한 형태의 증상이 나타나는 것을 말합니다.";
        }else if(code == 4){
            Disease = "유루증";
            info = "눈 주변의 털이 지속적으로 축축해지고 붉은색으로 변하는 현상을 유루증이라고 합니다. " +
                    "강아지의 눈물에는 털을 붉은색으로 변화시키는 색소가 포함되어 있어 말티즈나 푸들처럼 하얀 털을 " +
                    "갖고 있는 강아지의 경우, 눈 주위의 털이 붉게 변하게 됩니다.";
        }else if(code ==5){
            Disease = "색소침착성 각막염";
            info = "색소침착성 각막염은 각막에 색소침착이 발생하는 안구 질환입니다.."+
                    "색소침착성 각막염은 초기에 적절한 치료로 관리되면 진전을 늦출 수 있으므로, " +
                    "증상이 나타나면 반드시 수의사와 상담하고 치료 방법을 검토해야 합니다. ";

        }else if(code == 6){
            Disease = "궤양성 각막염";
            info = "강아지의 각막에 발생하는 괴사 또는 궤양으로 나타나는 안구 질환을 가리킵니다."+
                    "궤양성 각막질환은 즉시 수의사의 치료가 필요합니다. 치료는 각막에 약물을 바르거나 " +
                    "안약을 투여하여 염증 및 감염을 관리하고, " +
                    "상처를 치료하기 위한 안과 수술을 포함할 수 있습니다.";
        }else if(code == 7 ){
            Disease = "핵경화";
            info = "핵경화는 눈의 수정체와 연관된 눈의 변화를 가리키는 용어입니다. " +
                    "이 상태는 노화에 따른 정상적인 변화로 간주되며, 일반적으로 고령의 강아지에서 나타납니다."+
                    "핵경화는 비교적 무해한 눈의 변화로 간주되며, 주로 노화와 연관이 있습니다.";
        }else if(code == 8 ){
            Disease = "비궤양성 각막염";
            info = "비궤양성 각막질환은 강아지의 눈 각막(cornea)에 발생하는 감염이나 손상 없이 발생하는 각막 질환을 나타냅니다. " +
                    "비궤양성 각막질환은 각막에 염증 또는 상처 없이 각막 조직의 비정상적인 변화로 나타날 수 있습니다.";
        }else if(code == 9 ){
            Disease = "유리체 변성";
            info = "눈의 유리체체(혹은 유리체체 중벽)에 변화가 일어나는 상태를 가리킵니다."+
                    "유리체 변성은 눈의 자연적인 노화 과정의 일부로 간주되며, " +
                    "대부분의 경우 심각한 문제가 아니라는 점을 감안할 필요가 있습니다." +
                    "그러나 눈의 건강과 시력 변화에 대한 관리를 위해 수의사의 조언을 듣는 것이 중요합니다.";
        }

        return checkResponse.builder().disease(Disease).info(info).build();
    }

    public Boolean addWalk(WalkRequest request)throws Exception{
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(()->
                new IllegalArgumentException("Invalid member id: " + request.getPetId()));

        try{
            Walk walk = Walk.builder()
                    .walkDistance(request.getWalkDistance())
                    .pet(pet)
                    .walkDate(request.getWalkDate())
                    .walkID(request.getWalkId())
                    .walkTime(request.getWalkTime())
                    .build();
            walkRepository.save(walk);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public List<Walk> getWalkByPet(Long petId){

        return walkRepository.findAllByPetPetId(petId);
    }

    public List<Walk> getWalkByMemberId(Long memberId){
        return walkRepository.findByPetMemberId(memberId);
    }

    public List<Walk> getAllWalk(){
        return walkRepository.findAll();
    }


    public List<Object[]> getAverageWeightByBreed() {
        return petRepository.findAverageWeightByBreed();
    }

    public List<Object[]> getAverageWalk() {
        return walkRepository.findAverageWalk();
    }

    public List<Map<String, Object>> getAverageWalkInfoByBreed() {
        List<Object[]> averageWalkInfoByBreedList = walkRepository.findAverageWalkInfoByBreed();

        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Object[] result : averageWalkInfoByBreedList) {
            String breed = (String) result[0];
            Double averageWalkTime = (Double) result[1];
            Double averageWalkDistance = (Double) result[2];

            Map<String, Object> walkInfoMap = new HashMap<>();
            walkInfoMap.put("breed", breed);
            walkInfoMap.put("averageWalkTime", averageWalkTime);
            walkInfoMap.put("averageWalkDistance", averageWalkDistance);

            responseList.add(walkInfoMap);
        }

        return responseList;
    }

}
