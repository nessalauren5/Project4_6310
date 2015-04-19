package com.scheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.scheduler.model.*;
import com.scheduler.dataprovider.*;
import com.scheduler.math.*;
import gurobi.*;

public class Main {
	static DbConnect db;
	static HashMap<String,Student> studentHashMap;
	static HashMap<String,Course> courseHashMap;
	static String studentID;
	static String SemesterID;
	
/*	***
	 * NAME:		main
	 * DESCRIPTION:	Takes student ID as input and 
	 *//*
	public static void main(String[] args) {
		Solver gurobiSolver;
		
		if (args.length == 0) {
			System.out.println("error");
			System.out.println("No student specified");
			return;
		}
		if (args.length > 2) {
			System.out.println("error");
			System.out.println("Invalid number of arguments");
			return;
		}
		studentID = args[0];
		SemesterID = args[1];
		
		db= new DbConnect();
		courseHashMap = new HashMap<String,Course>();
		createStudentHashMap();
		gurobiSolver = new Solver(studentHashMap, courseHashMap, studentID);
		gurobiSolver.optimize();
		return;
	}*/

	private static void createStudentHashMap() {
		ResultSet RS;
		String studentID,CourseList,Credits;
		studentHashMap = new HashMap<String,Student>();
		
		RS = db.executeSQL("SELECT StudentID, CourseList FROM studentpreferences WHERE SemesterID='" + SemesterID + "'");
		
		try {
			while (RS.next()) {
				studentID = RS.getString("StudentID");
				CourseList = RS.getString("CourseList");
				insertStudent(studentID,CourseList);
			}
		} catch (SQLException e) {
		}
		
		RS = db.executeSQL("SELECT * FROM studentseniority");
		
		try {
			while (RS.next()) {
				studentID = RS.getString("StudentID");
				Credits = RS.getString("CreditsEarned");
				setStudentCredits(studentID, Credits);
			}
		} catch (SQLException e) {
		}
	}
	
	private static void insertStudent(String StudentID,String CourseList) {
		Student tempStudent;
		
		tempStudent = new Student();
		tempStudent.setStudentID(StudentID);
		tempStudent.setStudentPreferences(CourseList);
		studentHashMap.put(StudentID, tempStudent);
		updateCourseHashMap(StudentID, tempStudent.GetCourses());
	}
	
	private static void setStudentCredits(String StudentID, String Credits) {
		Student tempStudent = studentHashMap.get(StudentID);
		if (tempStudent != null) {
			studentHashMap.remove(StudentID);
			tempStudent.SetCredits(Credits);
			studentHashMap.put(StudentID, tempStudent);
		}
	}
	
	private static void updateCourseHashMap(String StudentID, String[] Courses) {
		if (Courses != null) {
			for (int i=0;i<Courses.length;i++) {
				addStudentToCourse(StudentID, Courses[i]);
			}
		}
	}
	
	private static void addStudentToCourse(String StudentID, String CourseID) {
		Course tempCourse = courseHashMap.get(CourseID);
		if (tempCourse != null) {
			courseHashMap.remove(CourseID);
		} else {
			tempCourse = new Course(CourseID);
		}
		tempCourse.AddStudent(StudentID);
		courseHashMap.put(CourseID, tempCourse);
	}
}
