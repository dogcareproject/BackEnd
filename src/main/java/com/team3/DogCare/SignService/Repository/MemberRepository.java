package com.team3.DogCare.SignService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.team3.DogCare.SignService.Domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Object> findByEmailAndAccount(String email, String account);
    Optional<Member> findByAccount(String account);
    Optional<Member> findByEmail(String email);


    //이곳에 DB연동과 필요한 함수 작성
    //Optional<Member> findByAccount(String account); -> Account로 DB에서 정보를 찾아 멤버로 반환
}
