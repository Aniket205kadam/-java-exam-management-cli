package com.aniket.App;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.aniket.model.Question;
import com.aniket.model.StudentTable;


public class Student {

	private HashMap<Integer, Integer> unique = new HashMap<>();
	
	//create a student account
	public void createStudent(SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);
		Boolean flag = false;
		
		Session session = sessionFactory.openSession();
		
		Transaction transaction = null;
		
		try {
			System.out.println();
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
			
			StudentTable table = new StudentTable();
			table.setName(name);
			table.setGmail(email);
			table.setPassword(password);
			table.setMarks(0);
			table.setOutOfMarks(0);
			
			transaction = session.beginTransaction();
			
			session.persist(table);
			flag = true;
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
	
	//log In to the student account
	public void logInStudent(SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
		
		System.out.println();
		System.out.print("Enter your email id: ");
		String email = scanner.next();
		
		System.out.print("Enter your password: ");
		String password = scanner.next();
		
		StudentTable checkEmail = session.get(StudentTable.class, email);
		if(checkEmail != null) {
			if(password.equals(checkEmail.getPassword())){
				System.out.println("\nHey "+checkEmail.getName() + ", your login Successful..!");
				studentControl(email, sessionFactory);
			}
			else {
				System.out.println("\nPassword InCorrect..!");
			}
		}
		else {
			System.out.println("\nEmail Id is not exist..!");
		}
	}

	//after login this control get to the student
	private void studentControl(String studentId, SessionFactory sessionFactory) {
		Scanner scanner = new Scanner(System.in);

		Integer studentChoise = -1;
		
		while(!studentChoise.equals(2)) {
			printOptions();
			studentChoise = scanner.nextInt();
			
			switch(studentChoise) {
				case 1 -> scheduleExam(studentId, sessionFactory);
				
				case 2 -> System.out.println("Log Out......");
				
			}
		}
	}
	
	public int randamUniqueValue(int min, int max) {
        Random r=new Random();
        int count = 0;
        Boolean flag = true;
        do {
            count = r.nextInt(min, max);
            if(!unique.containsKey(count)) {
                unique.put(count, 0);
                flag = false;
            } else {
                flag = true;
            }

        } while(flag);
        return count;
    }
	
	//exam control
	private void scheduleExam(String studentId, SessionFactory sessionFactory) {
	    Session session = sessionFactory.openSession();
	    Transaction transaction = null;
	    Scanner scanner = new Scanner(System.in);
	    Integer marks = 0;
	    Boolean flag = false;
	    
	    Integer count = (int) questionCount(sessionFactory);
	    Random random = new Random();
	    
	    Integer studentQuestions = count / 2 + 1;
	    
	    for (int i = 1; i <= studentQuestions; i++) {
	        Question question = session.get(Question.class, randamUniqueValue(1, studentQuestions + 1));
	        
	        System.out.println("\nQ" + i + ". " + question.getQuestion());
	        System.out.print("Ans: ");
	        String studentAnswer = scanner.nextLine();
	        
	        if(studentAnswer.equalsIgnoreCase(question.answerInString())) {
	            marks++;
	        }
	    }
	    
	    StudentTable studentTable = session.get(StudentTable.class, studentId);
	    if(marks > studentTable.getMarks() && studentQuestions >= studentTable.getOutOfMarks()) {
	        try {
		        StudentTable table = new StudentTable();
		        table.setName(studentTable.getName());
		        table.setGmail(studentTable.getGmail());
		        table.setPassword(studentTable.getPassword());
		        table.setMarks(marks);
		        table.setOutOfMarks(studentQuestions);
		        
		        transaction = session.beginTransaction();
		        
		        session.merge(table);
		        flag = true;
	        }
	        catch(HibernateException e) {
	        	e.printStackTrace();
	        }
	        catch (Exception e) {
				e.printStackTrace();
			}
	        finally {
	        	if (flag) {
					transaction.commit();
				}
	        	else {
	        		transaction.rollback();
	        	}
	        }
	    }
	    
	    System.out.println("\nComplete the exam you score " + marks + " out of " + studentQuestions);
	}

	//return questions count
	private long questionCount(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		Query<Long> query = session.createQuery("SELECT COUNT(q.question_id) FROM Question q", Long.class);
		Long count = query.uniqueResult();
		return count != null ? count : 0;
	}

	//print student option 
	private void printOptions() {
		System.out.println();
		System.out.println("1. Exam");
		System.out.println("2. Exist");
		System.out.print("Enter your choise: ");
	}
	
	//check the email id valid or not
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
