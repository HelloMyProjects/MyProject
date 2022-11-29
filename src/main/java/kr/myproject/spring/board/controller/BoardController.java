package kr.myproject.spring.board.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.myproject.spring.board.entity.Board;
import kr.myproject.spring.board.service.BoardService;
import kr.myproject.spring.member.service.MemberService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberService memberService;	
	@GetMapping("/board/boardWrite")
	public String boardWrite(Principal principal, Model model) {
		
		System.out.println("===========>username : " + principal.getName());

		String username = memberService.findByEmail(principal.getName()).getName();

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+username);
		model.addAttribute("username", username);
		
		return "board/boardWrite";
	}
	
	
	
}
