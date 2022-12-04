package kr.myproject.spring.member.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.myproject.spring.board.entity.Board;
import kr.myproject.spring.board.service.BoardService;
import kr.myproject.spring.member.dto.MemberFormDto;
import kr.myproject.spring.member.entity.Member;
import kr.myproject.spring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	private final BoardService boardService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/myPage")
	public String myPage(Principal principal ,Model model) {
		Member member = memberService.findByEmail(principal.getName());
		
		List<Board> board = boardService.findByMember(member.getName());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+board);
		model.addAttribute("member", member);
		model.addAttribute("board", board);
		return "member/myPage";
	}
	
	@PatchMapping("/myPage")
	public @ResponseBody ResponseEntity deleteAdmin(@RequestBody Map<String, Long> id, Model model) {

		Long project_id = id.get("id");
		System.out.println("===========>" + project_id);
		
		boardService.deleteById(project_id);
		
		return new ResponseEntity<Map>(id, HttpStatus.OK);
	}
	
	@GetMapping(value = "/myPage_modify")
	public String myPage_modify(Principal principal ,Model model) {
		Member member = memberService.findByEmail(principal.getName());
		
		List<Board> board = boardService.findByMember(member.getName());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>"+board);
		model.addAttribute("member", member);
		model.addAttribute("board", board);
		return "member/myPage_modify";
	}
	
	@PostMapping(value = "/myPage")
	public String myPageinfo(Principal principal, Model model, 
			@RequestParam("myinfo") String myinfo, @RequestParam("giturl") String giturl) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>infos : "+myinfo);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>infos : "+principal.getName());
		Member member = memberService.findByEmail(principal.getName());
		List<Board> board = boardService.findByMember(member.getName());
		
		member.setMyinfo(myinfo);
		member.setGiturl(giturl);
		memberService.save(member);
		
		model.addAttribute("member", member);
		model.addAttribute("board", board);
		
		
		
		return "member/myPage";
	}
	
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
