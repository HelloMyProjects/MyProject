package kr.myproject.spring.board.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping("/board/board")
	public String board(Model model) {
		List<Board> board = boardService.findAll();
		model.addAttribute("board", board);
		return "board/board";
	}
	
	@PostMapping("/manager/boardInsert")
	@ResponseBody
	public ResponseEntity boardInsert(Board board, Principal principal) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>board:"+board);
		String img = board.getContents();
		String i = "";
		try {
			i = img.split("src=")[1]; 
			if(i.length()==0) {
				System.out.println("으아아아아ㅏ앙ㄱ");
			}else {
				 String myimg = i.substring(1,i.indexOf("style=")-2);
				 System.out.println("=========>myimg : "+myimg);
				 board.setPhoto(myimg);
			}
		}catch (Exception e) {
			System.out.println("취소");
		}
        
		board.setMember(memberService.findByEmail(principal.getName()).getName());
		board.setRegdate(LocalDateTime.now());
		
		boardService.boardInsert(board);
		System.out.println("boardInsert 실행 : " + board.getContents());
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@GetMapping("board/boardDetail")
	public String boardDetail(@RequestParam Long id, Model model) {
		
		// List<Board> board=boardService.findAll();
		Optional<Board> list = boardService.findById(id);
		Board board = list.get();
		board.setViewcnt(board.getViewcnt()+1);
		boardService.saveBoard(board);
		System.out.println("boardDetail : " + board);

		model.addAttribute("board", board);
		return "board/boardDetail";
	}
	
}
