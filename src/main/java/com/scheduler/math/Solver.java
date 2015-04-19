package com.scheduler.math;

import java.util.ArrayList;
import com.scheduler.model.*;
import java.util.HashMap;
import java.util.Map;

import com.scheduler.model.Student;
import gurobi.*;

public class Solver {
	private HashMap<String,Student> studentHashMap;
	private HashMap<String,Course> courseHashMap;
	private HashMap<String, GRBVar> gurobiVaraibles;
	private Student TargetStudent;
	private GRBEnv environment;
	private GRBModel model;
	private String errorMessage;
	private final Double COURSE_LIMIT = 1.0;
	
	public Solver(HashMap<String,Student> studentHash, HashMap<String,Course> courseHash, String StudentID) {
		studentHashMap = studentHash;
		courseHashMap = courseHash;
		TargetStudent = studentHashMap.get(StudentID);
		errorMessage = "";
	}
	
	public void optimize() {
		try {
			  if (TargetStudent == null) {
				  errorMessage = "Student does not have any preferences";
				  printResult();
				  return;
			  }
			
		      environment = new GRBEnv("");
		      environment.set(GRB.IntParam.OutputFlag, 0);
		      model = new GRBModel(environment);

		      createVariables();
		      model.update();
		      SetObjectiveFunction();
		      AddConstraints();
		      model.optimize();
		      printResult();
		      model.dispose();
		      environment.dispose();
		    } catch (Exception e) {
		    	errorMessage = "Error occurred during optimization.";
		    }
	}
	
	private void printResult() {
		GRBVar variable;
		String StudentID;
		String[] Courses;
		
		if (errorMessage.length()>0) {
			System.out.println("error");
			System.out.println(errorMessage);
		} else {
			System.out.println("Success");
			Courses = TargetStudent.GetCourses();
			StudentID = TargetStudent.GetStudentID();
			try {
				if (Courses != null) {
					for (int i=0;i<Courses.length;i++) {
						variable = GetGRBVariable(StudentID, Courses[i]);
						System.out.println(Courses[i] + " " + variable.get(GRB.DoubleAttr.X));
					}
				}
			} catch (Exception e) {
				System.out.println("unable to print values");
			}
		}
	}
	
	private void createVariables() {
		String varDescriptor;
		String StudentID;
		String[] courses;
		Student tempStudent;
		String Credits;
		GRBVar variable;
		
		gurobiVaraibles = new HashMap<String, GRBVar>(); 
		try {
			for (Map.Entry<String, Student> entry : studentHashMap.entrySet()) {
				tempStudent = entry.getValue();
				courses = tempStudent.GetCourses();
				StudentID = tempStudent.GetStudentID();
				Credits = Integer.toString(tempStudent.GetCredits());
				if (courses != null) {
					for (int i=0; i< courses.length;i++) {
						varDescriptor = GetVariableKey(Credits,StudentID,courses[i]);
						variable = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, varDescriptor);
						gurobiVaraibles.put(varDescriptor, variable);
					}
				}
			}
		} catch(Exception e) {
			errorMessage = "Error occurred during variable creation.";
		}
	}
	
	private void SetObjectiveFunction() {
		GRBLinExpr expr = new GRBLinExpr();
		String varDescriptor;
		GRBVar variable;
		double NumCredits;
		
		try {
			for (Map.Entry<String, GRBVar> entry : gurobiVaraibles.entrySet()) {
				varDescriptor = entry.getKey();
				variable = entry.getValue();
				NumCredits = Double.parseDouble(varDescriptor.split("_")[1]);
				expr.addTerm(NumCredits, variable);
			}
			model.setObjective(expr, GRB.MAXIMIZE);
		} catch(Exception e) {
			errorMessage = "Error occurred while creating objective function.";
		}
		
	}
	
	private void AddConstraints() {
		AddClassSizeConstraints();
		AddEnrollmentConstraints();
	}
	
	//No course can have more than COURSE_LIMIT students enrolled
	private void AddClassSizeConstraints() {
		Course course;
		String courseID;
		GRBLinExpr expr;
		GRBVar variable;
		ArrayList<String> studentsArray;
		
		try {
			for (Map.Entry<String, Course> entry : courseHashMap.entrySet()) {
				courseID = entry.getKey();
				course = entry.getValue();
				studentsArray = course.GetStudentArray();
				expr = new GRBLinExpr();
				for (String StudentID : studentsArray) {
					variable = GetGRBVariable(StudentID,courseID);
					expr.addTerm(1.0, variable);
				}
				model.addConstr(expr, GRB.LESS_EQUAL, COURSE_LIMIT,"");
			}
		} catch(Exception e) {
			errorMessage = "Error occurred while adding class size constraints.";
		}
	}
	
	//No student can enroll in more than 3 courses
	//Students with less than six credits cannot enroll in more than 2 courses 
	private void AddEnrollmentConstraints() {
		Student student;
		String StudentID;
		GRBLinExpr expr;
		GRBVar variable;
		String[] courses;
		Double studentCourseLimit;
		Boolean needsConstraint;
		
		try {
			for (Map.Entry<String, Student> entry : studentHashMap.entrySet()) {
				student = entry.getValue();
				courses = student.GetCourses();
				StudentID = student.GetStudentID();
				
				if (courses != null) {
					needsConstraint = false;
					studentCourseLimit = student.GetCourseRestrictions();
					if (courses.length > 3) {
						needsConstraint = true;
					} else if ((courses.length > 2) && (studentCourseLimit < 3.0)) {
						needsConstraint = true;
					}
					if (needsConstraint) {
						expr = new GRBLinExpr();
						for (int i=0; i< courses.length;i++) {
							variable = GetGRBVariable(StudentID,courses[i]);
							expr.addTerm(1.0, variable);
						}
						model.addConstr(expr, GRB.LESS_EQUAL, studentCourseLimit,"");
					}
				}
			}
		} catch(Exception e) {
			errorMessage = "Error occurred while adding enrollment constraints.";
		}
	}	
	
	private GRBVar GetGRBVariable(String StudentID,String CourseID) {
		GRBVar variable;
		Student student;
		String varDescriptor;
		student = studentHashMap.get(StudentID);
		varDescriptor = GetVariableKey(Integer.toString(student.GetCredits()),StudentID,CourseID);
		variable = gurobiVaraibles.get(varDescriptor);
		return variable;
	}
	
	private String GetVariableKey(String Credits, String StudentID,String CourseID) {
		return "y_" + Credits + "_" + StudentID + "_" + CourseID;
	}
}
