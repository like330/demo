package com.like.firstboot.login.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.like.firstboot.pojo.User;

@RestController
public class LoginController {

	@RequestMapping("/login")
	public Object login(Model model) {
		
		return new User("123","123");
	}
}
