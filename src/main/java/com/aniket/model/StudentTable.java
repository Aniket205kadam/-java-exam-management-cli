package com.aniket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="students")
public class StudentTable {
	
	@Id
	@Column(name="student_email")
	private String gmail;
	
	@Column(name="student_name")
	private String name;
	
	@Column(name="student_password")
	private String password;
	
	@Column(name="student_marks")
	private Integer marks;
	
	@Column(name="out_of_marks")
	private Integer outOfMarks;
	
	//for the hibernate
	public StudentTable() {
		
	}
	
	public Integer getOutOfMarks() {
		return outOfMarks;
	}

	public void setOutOfMarks(Integer outOfMarks) {
		this.outOfMarks = outOfMarks;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
