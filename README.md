# SunBaseDataAssignment

## Task Description
You are tasked with integrating a set of RESTful APIs into a Spring Boot application. The APIs allow for user authentication, customer creation, customer retrieval, customer deletion, and customer updates. These APIs have specific endpoints, request parameters, and expected response codes.

## API Endpoints

1. **Authenticate User**
   - Endpoint: `POST` https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp
   - Parameters:
     - `login_id`: User's email
     - `password`: User's password
   - Response:
     - Successful Authentication: Bearer token
     - Authentication Failure: 401 (Invalid Authorization)

2. **Create a New Customer**
   - Endpoint: `POST` https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
   - Parameters:
     - `cmd`: create
   - Headers:
     - `Authorization`: Bearer token received in the authentication API
   - Body:
     - `first_name` (mandatory)
     - `last_name` (mandatory)
     - `street`
     - `address`
     - `city`
     - `state`
     - `email`
     - `phone`
   - Response:
     - Successful: 201 (Successfully Created)
     - Failure (if `first_name` or `last_name` is missing): 400 (First Name or Last Name is missing)

3. **Get Customer List**
   - Endpoint: `GET` https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
   - Parameters:
     - `cmd`: get_customer_list
   - Header:
     - `Authorization`: Bearer token received in the authentication API
   - Response:
     - Successful: 200 (List of customer details in JSON)

4. **Delete a Customer**
   - Endpoint: `POST` https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
   - Parameters:
     - `cmd`: delete
     - `uuid`: UUID of a specific customer
   - Header:
     - `Authorization`: Bearer token received in the authentication API
   - Response:
     - Successful: 200 (Successfully deleted)
     - Error (if UUID not found): 400 (UUID not found)
     - Error (other cases): 500 (Error Not deleted)

5. **Update a Customer**
   - Endpoint: `POST` https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
   - Parameters:
     - `cmd`: update
     - `uuid`: UUID of a specific customer
   - Header:
     - `Authorization`: Bearer token received in the authentication API
   - Body:
     - `first_name` (mandatory)
     - `last_name` (mandatory)
     - `street`
     - `address`
     - `city`
     - `state`
     - `email`
     - `phone`
   - Response:
     - Successful: 200 (Successfully Updated)
     - Error (if UUID not found): 500 (UUID not found)
     - Error (if the body is empty): 400 (Body is Empty)

## Implementation Steps
1. Create a basic HTML user interface with login, customer list view, create new customer, delete customer, and update customer functionality.
2. Implement a Spring Boot application with detailed code for controllers, services, and DTOs to handle API integration.
3. Implement error handling for various response status codes.
4. Configure application properties for API integration.
5. Implement RESTful endpoints for create, retrieve, update, and delete customers.
6. Implement Bearer token validation (e.g., using JWT) for user authentication.

**Note:** Testing with tools like Postman is recommended to verify API functionality.

This assignment will consist of three screens:
- Login Screen
- Customer List Screen
- Add a New Customer Screen
