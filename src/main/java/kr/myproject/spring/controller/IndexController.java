package kr.myproject.spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/template")
	public String main() {
		return "/template";
	}
	
}
