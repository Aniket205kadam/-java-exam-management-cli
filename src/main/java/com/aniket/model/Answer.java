package com.aniket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer answer_id;
	
	private String answer;
	
	//for the hibernate
	public Answer() {
		
	}
	
	//set the answer
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	//get the answer
	public String getAnswer() {
		return answer;
	}
	
	@Override
	public String toString() {
		return answer;
	}
}
