package com.aniket.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer question_id;
	
	private String question;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Answer answer;
	
	//for hibernate
	public Question() {
		
	}
	
	//setters
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	//Getters
	public String getQuestion() {
		return question;
	}
	
	public Answer getAnswer() {
		return answer;
	}
	
	@Override
	public String toString() {
		return question + " \nAns: " + answer; 
	}
	
	public String answerInString() {
		return answer.getAnswer();
	}
}
