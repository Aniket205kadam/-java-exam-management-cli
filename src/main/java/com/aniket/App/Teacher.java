package com.aniket.App;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.aniket.model.Answer;
import com.aniket.model.Question;
import com.aniket.model.TeacherTable;


public class Teacher {

	//this method is used to add questions and the answer
	private void addQuestionsAndAnswer(SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);
		Boolean flag = false;
		Session session = sessionFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			System.out.println();
			System.out.print("Enter a question: ");
			String que = scanner.nextLine();
			
			System.out.print("Enter a answer: ");
			String ans = scanner.nextLine();
			
			Question question = new Question();
			question.setQuestion(que);
			
			Answer answer = new Answer();
			answer.setAnswer(ans);
			
			question.setAnswer(answer);
			
			session.persist(question);
			flag = true;
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		catch (InputMismatchException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (flag) {
				System.out.println("\nQuestion and Answer added Successfully!");
				transaction.commit();
			}
			else {
				System.out.println("\nQuestion and Answer added Un-successfully!");
				transaction.rollback();
			}
		}
	}
	
	//create a teacher account
	public void createTeacher(SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);
		Boolean flag = false;
		
		Session session = sessionFactory.openSession();
		
		Transaction transaction = null;
		
		try {
			System.out.println();
			System.out.print("Enter Teacher verification code: ");
			String verificationCode = scanner.next();
			
			transaction = session.beginTransaction();
			
			if(verificationCode.equals("exam")) {
				System.out.print("Enter your name: ");
				String name = scanner.next();
				
				String email = null;
				myEmail : while(true) {
					System.out.print("Enter your email id: ");
					email = scanner.next();
					
					if(checkEmail(email)) {
						break myEmail;
					}
					else {
						System.out.println("\nYour email id is not valid...!");
					}
				}
				
				System.out.print("Enter your password: ");
				String password = scanner.next();
				
				TeacherTable table = new TeacherTable();
				table.setName(name);
				table.setEmail(email);
				table.setPassword(password);
				
				
				
				session.persist(table);
				flag = true;
			}
			else {
				System.out.println("\nYou entred wrong verification code....!");
				return;
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (flag) {
				System.out.println("\nSign Up successfully!");
				System.out.println("You can loging again and then you can able to add the questions");
				transaction.commit();
			}
			else {
				System.out.println("\nSign Up failed...!");
				transaction.rollback();
			}
		}
	}
	
	//log In to the Teacher account
	public void logInTeacher(SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
		
		System.out.println();
		System.out.print("Enter your email id: ");
		String email = scanner.next();
		
		System.out.print("Enter your password: ");
		String password = scanner.next();
		
		TeacherTable checkEmail = session.get(TeacherTable.class, email);
		if(checkEmail != null) {
			if(password.equals(checkEmail.getPassword())){
				System.out.println("\nHey "+checkEmail.getName() + ", your login Successful..!");
				teacherControl(sessionFactory);
				
			}
			else {
				System.out.println("\nPassword InCorrect..!");
			}
		}
		else {
			System.out.println("\nEmail Id is not exist..!");
		}
	}
	
	//after login this control get to the teacher
	private void teacherControl(SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);

		Integer teacherChoise = -1;
		
		while(!teacherChoise.equals(3)) {
			printOptions();
			teacherChoise = scanner.nextInt();
			
			switch(teacherChoise) {
				case 1 -> addQuestionsAndAnswer(sessionFactory);
				
				case 2 -> viewMarks(sessionFactory);
				
				case 3 -> {
					System.out.println("\nLog Out......");
				}
			}
		}
	}
	
	private void viewMarks(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		
		Query<Object[]> query = session.createQuery("SELECT name, marks, outOfMarks FROM StudentTable ORDER BY marks", Object[].class);
		List<Object[]> studentsMarks = query.list();

		System.out.println("\n\nStudent name	marks");
		for (Object[] row : studentsMarks) {
		    String name = (String) row[0];
		    Integer marks = (Integer) row[1];
		    Integer outOfMarks = (Integer) row[2];
		    System.out.println(name + "		" + marks + "/" + outOfMarks);
		}
	}
	
	//print teacher option 
	private void printOptions() {
		System.out.println();
		System.out.println("1. Set Questions and Answers");
		System.out.println("2. View marks of all students");
		System.out.println("3. Exist");
		System.out.print("Enter your choise: ");
	}
	
	private Boolean checkEmail(String email) {
        int length = email.length();

        for(int i = 0; i < length; i++) {
            if(email.charAt(i) == '@') {
                return true;
            }
        }
        return false;
    }
}
