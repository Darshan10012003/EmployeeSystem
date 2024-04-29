package com.employeesystem.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employeesystem.proxies.EmployeeProxies;

@Service
public interface EmployeeService {

	public Boolean ValidateToken();
	
	public String RegisterEmployee(MultipartFile multipartFile,EmployeeProxies employeeProxies);
	
	public List<EmployeeProxies> GetAllData();
	
	public String UpdateEmployee(String email,EmployeeProxies employeeProxies);
	
	public EmployeeProxies GetSingleEmployee(String email);
	
	public String SaveData(EmployeeProxies employeeProxies);
	
	public String DeleteEmployeeById(String email);
	
	public EmployeeProxies GetSingleEmployeeWithPassword(String name,String Password);
}
