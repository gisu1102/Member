package com.example.Member.service;

import com.example.Member.dto.MemberDTO;
import com.example.Member.entity.MemberEntity;
import com.example.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public MemberDTO login(MemberDTO memberDTO){
        /*
            1.회원이 입력한 이메일로 DB에서 조회를 함
            2.DB에서 조회한 비번과 사용자가 입력한 비번 일치 확인
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()){
            //계정 있다
            //optional로 가져온 객체 벗겨내기-get entity로 변환
            MemberEntity memberEntity = byMemberEmail.get();
            //데이터베이스 - 클라이언트(입력비번) 비교하기
            // == 은 레퍼런스 비교 ,  equals 는 값 비교 객체가 다르면
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //비번 일치
                //entity->dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;

            }else{
                //비번 불일치
                return null;
            }
        }else {
            //계정 없다
            return null;
        }

    }

    public List<MemberDTO> findALL() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        //    MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
        //    memberDTOList.add(memberDTO);

        }
        return memberDTOList;


    }

    //controller 로 보내니까 entity->dto , optional -> entity (get)
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
        //    MemberEntity memberEntity = optionalMemberEntity.get();
        //    MemberDTO memberDTO =MemberDTO.toMemberDTO(memberEntity);
            //    return  memberDTO;

            return MemberDTO.toMemberDTO(optionalMemberEntity.get());

        } else {
            return null;
        }


    }
}
