package com.example.Member.entity;

import com.example.Member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Entity
@Getter
@Setter
@Table(name = "MEMBER_TABLE")

public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //고유 id 키 값 auto_increment
    private Long id;
    //데이터베이스 테이블 에 매핑 - 일관성유지
    @Column  (unique = true) //unique  제약조건
    private String memberEmail;

    @Column
    private  String memberPassword;

    @Column
    private String memberName;

    // 각 매개변수 확인 잘하기 .
    //이 코드는 MemberDTO의 각 필드 값을 가져와서 MemberEntity의 해당 필드에 설정한 후, MemberEntity 객체를 반환.
    public  static MemberEntity toMemberEntity(MemberDTO memberDTO)
    {
        MemberEntity memberEntity = new MemberEntity();
        // MemberDTO에서 정보 추출 및 MemberEntity에 설정
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        // 기타 필요한 정보들을 MemberDTO에서 추출하여 MemberEntity에 설정

        //id 필드 빠져있는 이유 -> 데이터베이스에서 고유한 식별자 자동생성됨. (사용자가 입력하는 id = 고유한 식별자 , 데이터베이스의 테이블의 식별자와는 별개
        // -> 숫자형태의 일련번호 (sequence number) 가 별개로 자동생성 - 데베의 자동 증가 기능-> 데베고유식별자생성 -> 식별자관리 부담 없음, 일관성 유지 용이


        return  memberEntity;
    }
    public  static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO)
    {
        MemberEntity memberEntity = new MemberEntity();
        // update - id O -> id수정
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());

        return  memberEntity;
    }


}








/*
import javax.persistence.Entity;
import javax.persistence.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import  lombok.Getter;
import  lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@Table (name = "member_table")

public class MemberEntity {
    @id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
}
*/
