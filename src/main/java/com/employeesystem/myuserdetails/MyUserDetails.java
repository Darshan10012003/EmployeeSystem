package com.employeesystem.myuserdetails;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.employeesystem.entity.Employee;
import com.employeesystem.repo.EmployeeRepo;
@Configuration
@Component
public class MyUserDetails implements UserDetailsService{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Employee byName = employeeRepo.findByName(username);
		if (username.equals(byName.getName())) {
			return new User(username, passwordEncoder().encode(byName.getPassword()), new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("Employee Not Found..");
		}
		
	}

}
