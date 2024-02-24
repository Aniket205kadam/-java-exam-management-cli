package com.aniket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Teachers")
public class TeacherTable {

	@Id
	@Column(name="teacher_email")
	private String email;
	
	@Column(name="teacher_name")
	private String name;
	
	@Column(name="teacher_password")
	private String password;
	
	public TeacherTable() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
