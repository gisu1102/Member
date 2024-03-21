package com.example.Member.repository;

import com.example.Member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//데이터베이스에서 데이터와 상호작용하는 메소드 포함됨
// jpa 인터페이스 상속 , 메소드 정의 , 쿼리 메소드 , CRUD
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //이메일로 회원 정보 조회 select * from member_table where member_email=?

    //optional-> null방지
    //JPA 가 자동구현x -> 직접 구현
    //JPA가 메소드 의도 파악 - 명시적 쿼리 구현할필요없이 자동구현
    Optional<MemberEntity> findByMemberEmail(String memberEmail);


}
