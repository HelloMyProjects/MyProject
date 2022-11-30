package kr.myproject.spring.board.service;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.myproject.spring.board.entity.Board;
import kr.myproject.spring.board.repository.BoardRepository;


@Service
public class BoardService {
	@Autowired
	BoardRepository repository;
	
	public void boardInsert(Board list) {
		 Board itemList = repository.save(list);
	}
	
	public void saveBoard(Board board) {
		repository.save(board);
	}

	public List<Board> findFirst4ByOrderByIdDesc() {
		List<Board> board = repository.findFirst7ByOrderByIdDesc();
		return board;
	}
}
