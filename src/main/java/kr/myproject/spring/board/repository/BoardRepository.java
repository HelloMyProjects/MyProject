package kr.myproject.spring.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.myproject.spring.board.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long>{ // Board 엔티티
	
	List<Board> findFirst7ByOrderByIdDesc();
}
