package com.krish.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.krish.dto.CustomerRequest;
import com.krish.errorHandling.ApiRequestException;
import com.krish.errorHandling.UnauthorizedException;
import com.krish.model.Customer;
import com.krish.service.AuthService;
import com.krish.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AuthService authService;
	Map<String, CustomerRequest> customerList = new HashMap<>();

	@GetMapping("/customer")
	public String getCustomerList(Model model, HttpSession session) {
		try {
			String token = (String) session.getAttribute("token");
			List<Customer> customers = customerService.getCustomerList(token);
			if (customers != null) {
				model.addAttribute("customers", customers);
				for (Customer customer : customers) {
					CustomerRequest details = new CustomerRequest(customer.first_name, customer.getLast_name(),
							customer.getStreet(), customer.getAddress(), customer.getCity(), customer.getState(),
							customer.getEmail(), customer.getPhone());
					customerList.put(customer.uuid, details);
				}
				return "customerList.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/add")
	public String save() {
		return "add-customer.html";
	}

	@PostMapping("/create")
	public String createCustomer(@ModelAttribute CustomerRequest request, HttpSession session) {
		String response = authService.authenticateUser("test@sunbasedata.com", "Test@123");
		String token = response.split(":")[1].substring(1, response.split(":")[1].length() - 3);
		if (token == null) {
			throw new UnauthorizedException("Invalid Authorization");
		}
		try {
			customerService.createCustomer(token, request);
			session.setAttribute("token", token);

			return "redirect:/customer";

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/customers";
	}

	@PostMapping("/customer/delete")
	public String deleteCustomer(@RequestParam("uuid") String uuid, HttpSession session) {
		String response = authService.authenticateUser("test@sunbasedata.com", "Test@123");
		String token = response.split(":")[1].substring(1, response.split(":")[1].length() - 3);
		customerService.deleteCustomer(token, uuid);
		session.setAttribute("token", token);
		return "redirect:/customer";
	}

	@PostMapping("/edit")
	public ModelAndView updatethisCustomer(@RequestParam("uuid") String uuid, CustomerRequest request, ModelAndView mv,
			HttpSession session) {
		for (Map.Entry<String, CustomerRequest> entry : customerList.entrySet()) {
			if (entry.getKey().equals(uuid)) {
				request = entry.getValue();
			}
		}
		mv.addObject("customer", request);
		mv.setViewName("edit-customer.html");
		session.setAttribute("uuid", uuid);

		return mv;

	}

	@PostMapping("/customer/edit")
	public String updateCustomer(@ModelAttribute CustomerRequest request, HttpSession session) {
		String response = authService.authenticateUser("test@sunbasedata.com", "Test@123");
		String token = response.split(":")[1].substring(1, response.split(":")[1].length() - 3);
		String uuid = (String) session.getAttribute("uuid");
		customerService.updateCustomer(token, uuid, request);
		session.setAttribute("token", token);
		return "redirect:/customer";
	}
}
