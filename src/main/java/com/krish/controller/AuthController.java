package com.krish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.krish.service.AuthService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping("/")
	public String home() {
		return "login.html";
	}

	@PostMapping("/login")
	public String loginSuccess(@RequestParam String email, @RequestParam String password, HttpSession session) {
		if (!email.equals("test@sunbasedata.com") || !password.equals("Test@123")) {
			return "redirect:/";
		}
		String response = authService.authenticateUser(email, password);
		String token = response.split(":")[1].substring(1, response.split(":")[1].length() - 3);
		System.out.println(token);

		session.setAttribute("token", token);

		return "redirect:/customer";

	}
}
