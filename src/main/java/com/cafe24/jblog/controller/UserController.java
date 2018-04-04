package com.cafe24.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.UserVo;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	BlogService blogService;
	
	
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public String join() {

		return "user/join";
	}

	
	@RequestMapping(value = "join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo){

		boolean result = userService.userJoin(userVo);

		if (result == false) {
			return "user/join";
		}
		
		//login 되면서 블로그 관리 화면으로 이동
		blogService.makeNewBLog(userVo);
		
		return "redirect:/user/joinsuccess";
	}


	@RequestMapping(value = "joinsuccess", method = RequestMethod.GET)
	public String joinSuccess() {
		
		return "/user/joinsuccess";
	}
	

	@RequestMapping(value="login", method = RequestMethod.GET)
	public String login() {
		
		return "user/login";
	}

	
}
