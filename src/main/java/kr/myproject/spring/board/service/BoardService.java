package kr.myproject.spring.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.myproject.spring.board.entity.Board;
import kr.myproject.spring.board.repository.BoardRepository;


@Service
public class BoardService {
	@Autowired
	BoardRepository repository;
	
	public List<Board> findAll(){
		List<Board> board = repository.findAll();
		return board;
	}
	
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

	public Optional<Board> findById(Long id) {
		Optional<Board> board = repository.findById(id);
		return board;
	}
}
