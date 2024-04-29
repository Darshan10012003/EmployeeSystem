package com.employeesystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employeesystem.myuserdetails.MyUserDetails;
import com.employeesystem.proxies.EmployeeProxies;
import com.employeesystem.proxies.JwtRequest;
import com.employeesystem.proxies.JwtResponce;
import com.employeesystem.service.EmployeeService;
import com.employeesystem.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EmployeeController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetails myUserDetails;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponce> loginEmployee(@RequestBody JwtRequest jwtRequest) {
		if (jwtRequest != null) {
			try {
				Authentication authenticate = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(jwtRequest.getName(), jwtRequest.getPassword()));
				authenticate.isAuthenticated();
			} catch (Exception e) {
				// TODO: handle exception
				e.getMessage();
				throw new UsernameNotFoundException("user name not found!!");
			}
		}

		String token = null;
		UserDetails userByUsername = myUserDetails.loadUserByUsername(jwtRequest.getName());
		if (userByUsername != null) {
			token = jwtUtils.generateToken(userByUsername);
		}
		JwtResponce jwtResponce = new JwtResponce(token);
		return new ResponseEntity<JwtResponce>(jwtResponce, HttpStatus.OK);
	}

	@GetMapping("/validateToken")
	public Boolean ValidateToken() {
		return employeeService.ValidateToken();
	}

//	@PostMapping("/register")
//	public ResponseEntity<String> registerEmployee(@RequestParam("image") MultipartFile multipartFile ,@RequestParam("employeedata") String employeeProxies) throws JsonMappingException, JsonProcessingException {
//		System.out.println("datata");
//		ObjectMapper objectMapper = new ObjectMapper();
//		EmployeeProxies value = objectMapper.readValue(employeeProxies, EmployeeProxies.class);
//		return new ResponseEntity<String>(employeeService.RegisterEmployee(multipartFile, value),HttpStatus.OK);
//	}

	@GetMapping("/getAllData")
	public ResponseEntity<List<EmployeeProxies>> GetAllData() {
		return new ResponseEntity<List<EmployeeProxies>>(employeeService.GetAllData(), HttpStatus.OK);
	}

	@GetMapping("/getEmployee/{email}")
	public ResponseEntity<EmployeeProxies> GetSingleEmployee(@PathVariable String email) {
		return new ResponseEntity<EmployeeProxies>(employeeService.GetSingleEmployee(email), HttpStatus.OK);
	}

	@PostMapping("/savedata")
	public ResponseEntity<String> SaveData(@RequestBody EmployeeProxies employeeProxies) {
		return new ResponseEntity<String>(employeeService.SaveData(employeeProxies), HttpStatus.OK);
	}

	@PostMapping("/updateData/{email}")
	public ResponseEntity<String> updateData(@PathVariable String email, @RequestBody EmployeeProxies employeeProxies) {
		return new ResponseEntity<String>(employeeService.UpdateEmployee(email, employeeProxies), HttpStatus.OK);
	}

	@GetMapping("/deleteEmployee/{email}")
	public ResponseEntity<String> DeleteEmployeeById(@PathVariable String email) {
		return new ResponseEntity<String>(employeeService.DeleteEmployeeById(email), HttpStatus.OK);
	}
	
	@GetMapping("/getEmployee/{name}/{password}")
	public ResponseEntity<EmployeeProxies> GetSingleEmployeeWithPassword(@PathVariable String name,@PathVariable String password) {
		return new ResponseEntity<EmployeeProxies>(employeeService.GetSingleEmployeeWithPassword(name, password), HttpStatus.OK);
	}
}
