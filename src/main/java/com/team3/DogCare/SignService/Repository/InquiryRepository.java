package com.team3.DogCare.SignService.Repository;

import com.team3.DogCare.SignService.Domain.Inquiry;
import com.team3.DogCare.SignService.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    @Override
    Optional<Inquiry> findById(Long inquiryId);

    List<Inquiry>findAllByMemberId(Long MemberId);

}