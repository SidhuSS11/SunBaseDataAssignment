package com.krish.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.krish.dto.AuthRequest;
import com.krish.service.AuthService;

@RestController
@RequestMapping("/api/login")
public class AuthRestController {
	@Autowired
	AuthService authService;

	@PostMapping
	public ResponseEntity<String> loginSuccess(@RequestBody AuthRequest authRequest) {
		if (!authRequest.getLogin_id().equals("test@sunbasedata.com")
				|| !authRequest.getPassword().equals("Test@123")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid  email id or password");
		}
		String response = authService.authenticateUser(authRequest.getLogin_id(), authRequest.getPassword());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
