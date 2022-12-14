package kr.myproject.spring.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberFormDto {
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Length(min = 4, max = 16, message = "비밀번호는 4자 이상 16자 이하로 입력해주세요.")
	private String password;

	@NotBlank(message = "학과는 필수 입력 값입니다.")
	private String dept;

}
