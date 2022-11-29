package kr.myproject.spring.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.myproject.spring.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email);

}
