package com.example.Member.Controller;

import com.example.Member.dto.MemberDTO;
import com.example.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자주입
    private  final MemberService memberService;


    // 회원 가입 페이지 출력
    @GetMapping("/member/save")
    public  String saveForm(){
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
            //@RequestParam("memberEmail") String memberEmail,
            //@RequestParam("memberPassword") String memberPassword,
            //@RequestParam("memberName") String memberName){



        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);


        return "index";
    }

}
