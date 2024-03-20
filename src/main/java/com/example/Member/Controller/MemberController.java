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

    //회원가입 마치면 로그인 화면
        return "login";
    }
    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("member/login")
    //session -> 클라-서버 간 응답 사이 상태 정보 일시 저장
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null){
            //성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else {
            //실패
            return  "login";
        }

    }

    @GetMapping("/member/")
    public String findALL(Model model){
        List<MemberDTO> memberDTOList = memberService.findALL();
        // 어떠한 html로 가져갈 데이터 -> model
        model.addAttribute("memberList", memberDTOList);
        return  "list";

    }

    @GetMapping("/member/{id}")
    //경로상의 값 가져올때
    public String findById(@PathVariable Long id,Model model){
        MemberDTO memberDTO =memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

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
    //데이터 서버에 제출, 서버 상태변경
    @PostMapping("/member/update")
    //http에서 받은 값 필드에 매핑
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        //내정보를 수정하고나서 수정된 나의 상세페이지 반환
        return "redirect:/member/" + memberDTO.getId();
    }

    //URL 값 추출 - pathvariable
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        //redirect  -> 주소
        return "redirect:/member/";
    }

    // session 무효화
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }


}
