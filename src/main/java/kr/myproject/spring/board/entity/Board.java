package kr.myproject.spring.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MP_BOARD")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@ToString
@Transactional
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY : 기본키 생성을 데이터베이스에 위임
	@Column(name = "ID")
	private Long id;	
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "CONTENTS", nullable = false, length = 1000000000)
	private String contents;
	
	@Column(name = "MEMBER")
	private String member;
	
	@Column(name = "REGDATE")
	private LocalDateTime regdate;
	
	@Column(name = "VIEWCNT")
	private int viewcnt;
	
	@Column(name = "PHOTO", length = 100000000)
	private String photo;
	
	@Column(name="TEXT", length = 100000000)
	private String text;
	
	@Column(name="SIMPLE_INFO")
	private String simpleInfo;
	
}
