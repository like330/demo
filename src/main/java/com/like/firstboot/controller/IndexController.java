package com.like.firstboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/prod")
	public String indexprod() {
		return "index_prod";
	}
}
