package com.aniket.App;

import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class App {
	private static Scanner scanner = new Scanner(System.in);
	private static Teacher teacher = new Teacher();
	private static Student student = new Student();
	
	public static void main(String[] args) {
		SessionFactory sessionFactory = null;
		
		sessionFactory = new Configuration().configure().buildSessionFactory();
			
		mainLoop(sessionFactory);
	}
	
	private static void mainLoop(SessionFactory sessionFactory) {
		Integer userChoise = -1;
		
		while(!userChoise.equals(5)) {
			printOptions();
			System.out.print("Select your choise: ");
			userChoise = scanner.nextInt();
			
			switch (userChoise) {
				case 1 -> teacher.createTeacher(sessionFactory);
				
				case 2 -> student.createStudent(sessionFactory);
				
				case 3 -> teacher.logInTeacher(sessionFactory);
		
				case 4 -> student.logInStudent(sessionFactory);
				
				case 5 -> System.out.println("Exist...");
					
				default -> System.out.println("Enter wrong choise...!");
			}
		}
	}

	private static void printOptions() {
		System.out.println();
		System.out.println("1. Sign Up for Teacher");
		System.out.println("2. Sign Up for Student");
		System.out.println("3. Log In for Teacher");
		System.out.println("4. Log In for Student");
		System.out.println("5. exist");
	}
}

