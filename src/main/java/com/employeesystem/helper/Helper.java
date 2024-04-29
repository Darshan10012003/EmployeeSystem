package com.employeesystem.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employeesystem.entity.Employee;
import com.employeesystem.proxies.EmployeeProxies;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Helper {
	
	@Autowired
	ObjectMapper objectMapper;
	
	public Employee proxytoentity(EmployeeProxies employeeProxies) {
		Employee convertValue = objectMapper.convertValue(employeeProxies, Employee.class);
		return convertValue;
	}
	
	public EmployeeProxies entitytoproxie(Employee employee) {
		EmployeeProxies convertValue = objectMapper.convertValue(employee, EmployeeProxies.class);
		return convertValue;
	}

}
