package com.team3.DogCare.SignService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.team3.DogCare.SignService.Domain.Member

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>{

}
