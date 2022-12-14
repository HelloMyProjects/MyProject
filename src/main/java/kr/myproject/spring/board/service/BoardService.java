package kr.myproject.spring.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.myproject.spring.board.entity.Board;
import kr.myproject.spring.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BoardService {
	@Autowired
	BoardRepository repository;
	
	public List<Board> findAll(){
		List<Board> board = repository.findAll();
		return board;
	}
	
	public Page<Board> findPageAll(Pageable pageable){
		return repository.findAll(pageable);
	}
	
	public void boardInsert(Board list) {
		 Board itemList = repository.save(list);
	}
	
	public void saveBoard(Board board) {
		repository.save(board);
	}

	public List<Board> findFirst4ByOrderByIdDesc() {
		List<Board> board = repository.findFirst6ByOrderByIdDesc();
		return board;
	}

	public Optional<Board> findById(Long id) {
		Optional<Board> board = repository.findById(id);
		return board;
	}
	
	public List<Board> findByMember(String member){
		List<Board> board = repository.findByMember(member);
		return board;
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
