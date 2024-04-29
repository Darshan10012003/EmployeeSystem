package com.employeesystem.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.employeesystem.entity.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, String> {
	Employee findByName(String name);
	
	Employee findByNameAndPassword(String name,String password);
}
