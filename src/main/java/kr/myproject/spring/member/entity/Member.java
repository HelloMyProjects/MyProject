package kr.myproject.spring.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.myproject.spring.member.constant.Role;
import kr.myproject.spring.member.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mp_member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String dept;
	
	private String myinfo;
	
	private String giturl;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		Member member = new Member();
		
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setDept(memberFormDto.getDept());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(Role.ADMIN);
		
		return member;
		
	}

}
