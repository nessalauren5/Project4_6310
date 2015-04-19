package com.scheduler.constraints;
import gurobi.GRB;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.HashMap;
import java.util.Map;

import com.scheduler.exceptions.*;
import com.scheduler.math.Solver;
import com.scheduler.model.Course;
import com.scheduler.model.Student;

public class ClassPerSemesterConstraint {
	
	
	//No student can enroll in more than 3 courses
	//Students with less than six credits cannot enroll in more than 2 courses 
	public void AddEnrollmentConstraints(HashMap<String,Student> studentHashMap,
			HashMap<String,Course> courseHashMap, Student targetStudent, double courseLimit, GRBModel model) {
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
				StudentID = student.getStudentID();
				
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
						Solver tempsolver = new Solver(studentHashMap, courseHashMap, targetStudent.getStudentID());
						for (int i=0; i< courses.length;i++) {
							variable = tempsolver.GetGRBVariable(StudentID,courses[i]);
							expr.addTerm(1.0, variable);
						}
						model.addConstr(expr, GRB.LESS_EQUAL, studentCourseLimit,"");
					}
				}
			}
		} catch(Exception e) {
			ConstraintException ex = new ConstraintException(targetStudent.getStudentID(), e.getMessage());
		}
	}	
}
