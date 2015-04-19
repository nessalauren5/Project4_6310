/**
 * 
 */
package com.scheduler.constraints;

import gurobi.GRB;
import gurobi.GRBLinExpr;
import gurobi.GRBVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.scheduler.model.Course;
import com.scheduler.model.Student;
import com.scheduler.math.*;
import com.scheduler.exceptions.*;
import gurobi.*;

/**
 * @author ubuntu
 *
 */
public class ClassLimitConstraint {
	
	//No course can have more than COURSE_LIMIT students enrolled
		public void AddClassSizeConstraints(HashMap<String,Student> studentHashMap,
				HashMap<String,Course> courseHashMap, Student targetStudent, double courseLimit, GRBModel model) {
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
					Solver tempsolver = new Solver(studentHashMap, courseHashMap, targetStudent.getStudentID());
					for (String StudentID : studentsArray) {
						variable = tempsolver.GetGRBVariable(StudentID,courseID);
						expr.addTerm(1.0, variable);
					}
					model.addConstr(expr, GRB.LESS_EQUAL, courseLimit,"");
				}
			} catch(Exception e) {
				ConstraintException ex = new ConstraintException(targetStudent.getStudentID(), e.getMessage());
			}
		}

}
