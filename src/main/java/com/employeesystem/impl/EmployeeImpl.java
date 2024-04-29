package com.employeesystem.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.employeesystem.entity.Employee;
import com.employeesystem.helper.Helper;
import com.employeesystem.proxies.EmployeeProxies;
import com.employeesystem.repo.EmployeeRepo;
import com.employeesystem.service.EmployeeService;

@Component
public class EmployeeImpl implements EmployeeService {

	@Autowired
	Helper helper;

	@Autowired
	EmployeeRepo employeeRepo;

	@Override
	public Boolean ValidateToken() {
		// TODO Auto-generated method stub
		return Boolean.TRUE;
	}

	@Override
	public String RegisterEmployee(MultipartFile multipartFile, EmployeeProxies employeeProxies) {
		// TODO Auto-generated method stub
		try {
			final String absolutePath = new ClassPathResource("/image").getFile().getAbsolutePath();
			String originalFilename = multipartFile.getOriginalFilename();
			String fullpath = absolutePath + File.separator + originalFilename;
			Files.copy(multipartFile.getInputStream(), Path.of(fullpath), StandardCopyOption.REPLACE_EXISTING);
			String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/")
					.path(multipartFile.getOriginalFilename()).toUriString();

			Employee employee = new Employee();
			employee.setEmail(employeeProxies.getEmail());
			employee.setName(employeeProxies.getName());
			employee.setPassword(employeeProxies.getPassword());
			employee.setBirthDate(employeeProxies.getBirthDate());
			employee.setGender("male");
			employee.setImage(uriString);

			employeeRepo.save(employee);
			return "Save Successfully..";
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return null;
	}

	@Override
	public List<EmployeeProxies> GetAllData() {
		// TODO Auto-generated method stub
		List<EmployeeProxies> list = new ArrayList<>();
		List<Employee> allEmployees = (List<Employee>) employeeRepo.findAll();
		for (Employee oneEmployees : allEmployees) {
			EmployeeProxies entitytoproxie = helper.entitytoproxie(oneEmployees);
			list.add(entitytoproxie);
		}
		System.out.println(list);
		return list;
	}

	@Override
	public String UpdateEmployee(String email, EmployeeProxies employeeProxies) {
		// TODO Auto-generated method stub
		Optional<Employee> byId = employeeRepo.findById(email);
		Employee employee2 = byId.get();
		if (byId.isPresent()) {
			Employee employee = new Employee();
			employee.setEmail(email);
			employee.setPassword(employee2.getPassword());
			employee.setImage(employeeProxies.getImage());
			employee.setBirthDate(employeeProxies.getBirthDate());
			employee.setCompanyname(employeeProxies.getCompanyname());
			employee.setGender(employeeProxies.getGender());
			employee.setName(employeeProxies.getName());

			// Employee proxytoentity = helper.proxytoentity(employeeProxies);
			employeeRepo.save(employee);
			return "Update Successfully..";
		}
		return "Id Not Found..";
	}

	@Override
	public EmployeeProxies GetSingleEmployee(String email) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepo.findById(email).get();
		EmployeeProxies entitytoproxie = helper.entitytoproxie(employee);
		return entitytoproxie;
	}

	@Override
	public String SaveData(EmployeeProxies employeeProxies) {
		// TODO Auto-generated method stub

		Employee employee = new Employee();
		employee.setEmail(employeeProxies.getEmail());
		employee.setName(employeeProxies.getName());
		employee.setPassword(employeeProxies.getPassword());
		employee.setBirthDate(employeeProxies.getBirthDate());
		employee.setGender("male");
		employee.setImage(employeeProxies.getImage());

		employeeRepo.save(employee);
		return "Save Successfully..";
	}

	@Override
	public String DeleteEmployeeById(String email) {
		// TODO Auto-generated method stub
		Optional<Employee> byId = employeeRepo.findById(email);
		if (byId.isPresent()) {
			employeeRepo.deleteById(email);
			return "Deleted Successfully...";
		}
		return "Email Not Found..";
	}

	@Override
	public EmployeeProxies GetSingleEmployeeWithPassword(String name, String Password) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepo.findByNameAndPassword(name, Password);
		EmployeeProxies entitytoproxie = helper.entitytoproxie(employee);
		return entitytoproxie;
	}
}
