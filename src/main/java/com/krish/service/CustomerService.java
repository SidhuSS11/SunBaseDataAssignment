package com.krish.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krish.dto.CustomerRequest;
import com.krish.errorHandling.ApiRequestException;
import com.krish.errorHandling.BadRequestException;
import com.krish.errorHandling.UnauthorizedException;
import com.krish.model.*;

@Service
public class CustomerService {
	@Autowired
	private RestTemplate restTemplate;

	public List<Customer> getCustomerList(String authToken) {
		try {
		String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
		String url = apiUrl + "?cmd=get_customer_list";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString;
		
			jsonString = objectMapper.writeValueAsString(entity);
		
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
		ResponseEntity<List<Customer>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Customer>>() {
				});
	
		return response.getBody();
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}


	public int createCustomer(String authToken, CustomerRequest requestCustomer)  {
		String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
		String url = apiUrl + "?cmd=create";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		
		 CustomerRequest customer = new CustomerRequest();
	        customer.setFirst_name("");
	        customer.setLast_name("");
	        customer.setStreet("");
	        customer.setAddress("");
	        customer.setCity("");
	        customer.setState("");
	        customer.setEmail("");
	        customer.setPhone("");

	        // Update the fields in the customer object with the values from the requestCustomer
	        if (requestCustomer.getFirst_name() != null) {
	            customer.setFirst_name(requestCustomer.getFirst_name());
	        }
	        if (requestCustomer.getLast_name() != null) {
	            customer.setLast_name(requestCustomer.getLast_name());
	        }
	        if (requestCustomer.getStreet() != null) {
	            customer.setStreet(requestCustomer.getStreet());
	        }
	        if (requestCustomer.getAddress() != null) {
	            customer.setAddress(requestCustomer.getAddress());
	        }
	        if (requestCustomer.getCity() != null) {
	            customer.setCity(requestCustomer.getCity());
	        }
	        if (requestCustomer.getState() != null) {
	            customer.setState(requestCustomer.getState());
	        }
	        if (requestCustomer.getEmail() != null) {
	            customer.setEmail(requestCustomer.getEmail());
	        }
	        if (requestCustomer.getPhone() != null) {
	            customer.setPhone(requestCustomer.getPhone());
	        }
		HttpEntity<CustomerRequest> entity = new HttpEntity<>(customer, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		if (response.getStatusCodeValue() == 201 &&( customer.getFirst_name()!= null &&customer.getLast_name()!=null )) {
			return response.getStatusCodeValue();
		}
		return 0; 

	}

	public int deleteCustomer(String authToken, String uuid) {
		String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

		String url = apiUrl + "?cmd=delete&uuid=" + uuid;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			if (response.getStatusCodeValue() == 200) {
				return response.getStatusCodeValue();
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int updateCustomer(String authToken, String uuid, CustomerRequest request) {
		String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
		String url = apiUrl + "?cmd=update&uuid=" + uuid;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		HttpEntity<CustomerRequest> entity = new HttpEntity<>(request, headers);
	
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			if (response.getStatusCodeValue() == 200) {
				return response.getStatusCodeValue();
			} 
		} catch (HttpClientErrorException ex) {
			throw new UnauthorizedException("Invalid Authorization");
		}
		return 0;
	}
		
}
