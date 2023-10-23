package com.krish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.krish.dto.AuthRequest;

@Service
public class AuthService {
	private final RestTemplate restTemplate;

	@Autowired
	public AuthService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String authenticateUser(String email, String password) {
		String authApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		AuthRequest authRequest = new AuthRequest(email, password);
		HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(authApiUrl, HttpMethod.POST, requestEntity,
				String.class);
		return responseEntity.getBody();
	}
}
