package kr.myproject.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.myproject.spring.board.entity.Board;
import kr.myproject.spring.board.service.BoardService;

@Controller
public class IndexController {
	
	@Autowired
	BoardService boardService;
	
	@GetMapping(value = {"/", "/index"})
	public String index(Model model) {
		List<Board> board =  boardService.findFirst4ByOrderByIdDesc();
		model.addAttribute("board", board);
		return "/index";
	}
	
	
	@GetMapping("/template")
	public String main() {
		return "/template";
	}
	
	@GetMapping("/pic")
	public String pic() {
		return "/pic";
	}

	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	
}
