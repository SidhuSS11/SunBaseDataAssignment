package com.krish.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.krish.dto.CustomerRequest;
import com.krish.model.Customer;
import com.krish.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService; // Inject your CustomerService here

	private Map<String, CustomerRequest> customerList = new HashMap<>();

	// Create a new customer
	@PostMapping
	public ResponseEntity<String> createCustomer(@RequestHeader("Authorization") String authToken,
			@RequestBody CustomerRequest customer) {
		try {
			if (!authToken.equals("Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorisation");
			}

			else {
				int response = customerService.createCustomer(authToken, customer);
				if (customer.getFirst_name() != null && customer.getLast_name() != null) {

					return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
				} else if (customer.getFirst_name() == null || customer.getLast_name() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First_name or last_name missing");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// Get a list of customers
	@GetMapping
	public ResponseEntity<List<Customer>> getCustomerList(@RequestHeader("Authorization") String authToken) {

		try {

			// String token=authToken.substring(7,authToken.length());
			List<Customer> customers = customerService.getCustomerList(authToken);
			if (customers != null) {
				for (Customer customer : customers) {
					CustomerRequest details = new CustomerRequest(customer.first_name, customer.getLast_name(),
							customer.getStreet(), customer.getAddress(), customer.getCity(), customer.getState(),
							customer.getEmail(), customer.getPhone());
					customerList.put(customer.getUuid(), details);
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(customers);
		} catch (Exception e) {

		}
		return null;

	}

	// Update a customer
	@PutMapping("/{uuid}")
	public ResponseEntity<String> updateCustomer(@RequestHeader("Authorization") String authToken,
			@PathVariable String uuid, @RequestBody CustomerRequest customer) {
		if (!authToken.equals("Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorisation");
		}
		System.out.println(customer);
		if (customer == null || customer.equals(null)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body is Empty");
		}
		CustomerRequest customer1 = new CustomerRequest();
		List<Customer> customers;
		try {
			customers = customerService.getCustomerList(authToken);

			if (customers != null) {
				for (Customer k : customers) {
					CustomerRequest details = new CustomerRequest(k.first_name, k.getLast_name(), k.getStreet(),
							k.getAddress(), k.getCity(), k.getState(), k.getEmail(), k.getPhone());
					customerList.put(k.getUuid(), details);
				}

				if (!customerList.containsKey(uuid)) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUID not found");
				}
				for (Map.Entry<String, CustomerRequest> entry : customerList.entrySet()) {
					if (entry.getKey().equals(uuid)) {
						customer1 = entry.getValue();
					}
				}

				if (customer.getFirst_name() == null && customer1.getFirst_name() != null) {
					customer.setFirst_name(customer1.getFirst_name());
				}
				if (customer.getLast_name() == null && customer1.getLast_name() != null) {
					customer.setLast_name(customer1.getLast_name());
				}
				if (customer.getStreet() == null && customer1.getStreet() != null) {
					customer.setStreet(customer1.getStreet());
				}
				if (customer.getAddress() == null && customer1.getAddress() != null) {
					customer.setAddress(customer1.getAddress());
				}
				if (customer.getCity() == null && customer1.getCity() != null) {
					customer.setCity(customer1.getCity());
				}
				if (customer.getState() == null && customer1.getState() != null) {
					customer.setState(customer1.getState());
				}
				if (customer.getEmail() == null && customer1.getEmail() != null) {
					customer.setEmail(customer1.getEmail());
				}
				if (customer.getPhone() == null && customer1.getPhone() != null) {
					customer.setPhone(customer1.getPhone());
				}
				int updated = customerService.updateCustomer(authToken, uuid, customer);
				return ResponseEntity.status(HttpStatus.OK)
						.body("Customer with uuid " + uuid + " is updated successfully");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UUID not found");
		}

		return null;
	}

	// Delete a customer
	@DeleteMapping("/{uuid}")
	public ResponseEntity<String> deleteCustomer(@RequestHeader("Authorization") String authToken,
			@PathVariable String uuid) {
		if (!authToken.equals("Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorisation");
		}
		try {
			List<Customer> customers;

			customers = customerService.getCustomerList(authToken);
			if (customers != null) {
				for (Customer k : customers) {
					CustomerRequest details = new CustomerRequest(k.first_name, k.getLast_name(), k.getStreet(),
							k.getAddress(), k.getCity(), k.getState(), k.getEmail(), k.getPhone());
					customerList.put(k.getUuid(), details);
				}
				if (!customerList.containsKey(uuid)) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUID is not found");
				}
				int response = customerService.deleteCustomer(authToken, uuid);

				customerList.remove(uuid);
				return ResponseEntity.status(HttpStatus.OK)
						.body("Customer with uuid as " + uuid + " is deleted successfully");

			}
			if (uuid == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error not deleted");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
