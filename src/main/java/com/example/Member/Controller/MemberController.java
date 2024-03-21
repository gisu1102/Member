package com.example.Member.Controller;

import com.example.Member.dto.MemberDTO;
import com.example.Member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import java.util.List;
@Controller
@RequiredArgsConstructor

//Controller - 회원관리 - 각 메소드에서 수행 -> 서비스 클래스(비즈니스 로직)(DTO,Entity) -Database(JPARepository)
//Client- session, model
//회원가입, 로그인, 조회, 상세정보 수정, 삭제, 로그아웃 - 메소드
//
public class MemberController {
    //생성자주입
    private  final MemberService memberService;


    // 1.회원가입
    @GetMapping("/member/save")
    //회원가입 버튼 클릭 시 saveform -> 회원가입페이지(save.html) 출력
    public  String saveForm(){
        return "save";
    }

    //데이터베이스에 회원가입정보 전달
    @PostMapping("/member/save")
    //html폼 웹요청데이터-> memberDTO 객체로 담는다
    public String save(@ModelAttribute MemberDTO memberDTO){
            //@RequestParam("memberEmail") String memberEmail,
            //@RequestParam("memberPassword") String memberPassword,
            //@RequestParam("memberName") String memberName){
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

    //회원가입 마치면 로그인 화면
        return "login";
    }


    //2.로그인
    @GetMapping("/member/login")
    //로그인 버튼 클릭 시 loginForm -> 로그인 페이지
    public String loginForm(){
        return "login";
    }

    @PostMapping("member/login")
    //session -> 클라-서버 간 응답 사이 상태 정보 일시 저장
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null){
            //성공 - 세션에 이메일 정보 저장
            //세션 속성 할당 메소드 - loginResult 객체에서 이메일 가져오기
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else {
            //실패 - 다시 로그인 창
            return  "login";
        }

    }

    //3.모든 회원목록 조회
    @GetMapping("/member/")
    public String findALL(Model model){
        List<MemberDTO> memberDTOList = memberService.findALL();
        // 어떠한 html로 가져갈 데이터 -> model
        model.addAttribute("memberList", memberDTOList);
        return  "list";

    }

    //특정 회원의 상세정보 조회
    @GetMapping("/member/{id}")
    //URL 경로상의 값 가져올때 / 경로상의 추출한 값 , model
    public String findById(@PathVariable Long id,Model model){
        MemberDTO memberDTO =memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    //4. 상세정보 수정
    //업데이트할 페이지 접속 정보요청
    @GetMapping("/member/update")

    public  String updateForm(HttpSession session, Model model){
        //model-> object 이므로 캐스팅 -> string 변환
        //사용자가 로그인한 이메일 정보
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";


    }
    //데이터 서버에 제출, 회원 정보 업데이트 요청
    @PostMapping("/member/update")
    //http에서 받은 값 필드에 매핑
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        //내정보를 수정하고나서 수정된 "나의" 상세페이지 반환
        return "redirect:/member/" + memberDTO.getId();
    }

    //5.삭제
    //URL 값 추출 - pathvariable
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        //redirect  -> 주소
        return "redirect:/member/";
    }

    //6.로그아웃
    // session 무효화
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }


}
