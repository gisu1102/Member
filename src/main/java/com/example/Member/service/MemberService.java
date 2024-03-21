package com.example.Member.service;

import com.example.Member.dto.MemberDTO;
import com.example.Member.entity.MemberEntity;
import com.example.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // spring  에서 service 역할
@RequiredArgsConstructor //lombok 에너테이션 - final에대한 생성자 자동생성

//비즈니스 로직 처리 / Controller(DTO) , Datebase(Entity)
//save, login, findAll, findById, updateForm, update, deleteById 메소드 구현

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
            //optional로 가져온 객체 벗겨내기-get- entity로 변환
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


    public MemberDTO findById(Long id) {
        //id = null 일 가능성 -> optional
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
        //    MemberEntity memberEntity = optionalMemberEntity.get();
        //    MemberDTO memberDTO =MemberDTO.toMemberDTO(memberEntity);
            //    return  memberDTO;
            // controller 로 보내니까 optional -> entity (get) , entity->dto ,
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());

        } else {
            return null;
        }


    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if  (optionalMemberEntity.isPresent()){
            return  MemberDTO.toMemberDTO(optionalMemberEntity.get());

        }else {
            return null;
        }

    }

    public void update(MemberDTO memberDTO) {
        //save id insert update 알아서 , db - dto->entity
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));

    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
