package kr.myproject.spring.member.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.myproject.spring.member.dto.MemberFormDto;
import kr.myproject.spring.member.entity.Member;
import kr.myproject.spring.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	// 회원가입 페이지 연결
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	// 로그인 페이지 연결
	@GetMapping("/login")
	public String loginMember() {
		return "login";
	}
	
	// 회원가입시 성공, 실패
	@PostMapping(value = "/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
			
		} catch (IllegalStateException e) {

			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		
		}
		
		return "redirect:/";
		
	}
	
	// 로그인 에러시
	@GetMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "login";
	}

}
