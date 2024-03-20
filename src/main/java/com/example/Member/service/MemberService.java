package com.example.Member.service;

import com.example.Member.dto.MemberDTO;
import com.example.Member.entity.MemberEntity;
import com.example.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entitiy 변환!
        // 2. repository save메소드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save((memberEntity));

        //repository 의 save 메소드 호출 (조건. entity의 객체를 넘겨야한다 -> save 는 jpa 에서 제공




    }
}
