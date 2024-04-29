package com.employeesystem.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id
	private String email;

	private String name;

	private String address;

	private String education;

	private String city;

	private String gender;

	private String companyname;

	private Date birthDate;

	private String password;

	private String image;
}
