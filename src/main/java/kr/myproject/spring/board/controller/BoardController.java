package kr.myproject.spring.board.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public String board(Model model, @PageableDefault(size=12,sort="id", direction = Sort.Direction.DESC)Pageable pageable) {
		//List<Board> board = boardService.findAll();
		Page<Board> board = boardService.findPageAll(pageable);
		
		int nowPage =  board.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, board.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
		model.addAttribute("board", board);
		return "board/board";
	}
	
	@PostMapping("/board/boardInsert")
	@ResponseBody
	public ResponseEntity boardInsert(Board board, Principal principal) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>board:"+board);
		String img = board.getContents();
		String i = "";
		try {
			i = img.split("src=")[1]; 
			if(i.length()==0) {
				System.out.println("????????????????????????");
			}else {
				 String myimg = i.substring(1,i.indexOf("style=")-2);
				 System.out.println("=========>myimg : "+myimg);
				 board.setPhoto(myimg);
			}
		}catch (Exception e) {
			System.out.println("??????");
		}
        
		board.setMember(memberService.findByEmail(principal.getName()).getName());
		board.setRegdate(LocalDateTime.now());
		
		boardService.boardInsert(board);
		System.out.println("boardInsert ?????? : " + board.getContents());
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@GetMapping("board/boardDetail")
	public String boardDetail(@RequestParam Long id, Model model, Principal principal) {
		
		// List<Board> board=boardService.findAll();
		
		Optional<Board> list = boardService.findById(id);
		Board board = list.get();
		
		String email = memberService.findEmail(board.getMember());
		board.setViewcnt(board.getViewcnt()+1);
		boardService.saveBoard(board);
		System.out.println("boardDetail : " + board);

		model.addAttribute("board", board);
		model.addAttribute("email", email);
		return "board/boardDetail";
	}
	
	@GetMapping("board/boardModify")
	public String boardModify(@RequestParam Long id, Model model, Principal principal) {
		// List<Board> board=boardService.findAll();
		Optional<Board> list = boardService.findById(id);
		Board board = list.get();
		
		String username = memberService.findByEmail(principal.getName()).getName();


		model.addAttribute("username", username);
		model.addAttribute("board", board);
		return "board/boardModify";
	}
	
	@PatchMapping("/manager/boardModify")
	@ResponseBody
	public ResponseEntity boardModify(Board board, Principal principal) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>board:"+board);
		System.out.println("?????? ?????????");
		String img = board.getContents();
		String i = "";
		try {
			i = img.split("src=")[1]; 
			if(i.length()==0) {
				System.out.println("????????????????????????");
			}else {
				String myimg = i.substring(1,i.indexOf("style=")-2);
				System.out.println("=========>myimg : "+myimg);
				board.setPhoto(myimg);
			}
		}catch (Exception e) {
			System.out.println("??????");
		}
		
		board.setMember(memberService.findByEmail(principal.getName()).getName());
		board.setRegdate(LocalDateTime.now());
		
		boardService.boardInsert(board);
		System.out.println("boardmodify ?????? : " + board.getContents());
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/board/boardDelete", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteEvent(@RequestBody Board board) {
		System.out.println("?????? ??????");
		System.out.println(board);
		Long id = board.getId();
		
		boardService.deleteById(id);
		
		return new ResponseEntity<List<Board>>(HttpStatus.OK);
    }
}
