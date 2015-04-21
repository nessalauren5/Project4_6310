package com.scheduler.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.bytecode.stackmap.TypeData.ClassName;

import com.scheduler.constraints.ClassLimitConstraint;
import com.scheduler.constraints.ClassPerSemesterConstraint;
import com.scheduler.model.*;

import gurobi.*;

public class Solver {
	private static final Logger log = Logger.getLogger( ClassName.class.getName() );
	private HashMap<String,Student> studentHashMap;
	private HashMap<String,Course> courseHashMap;
	private HashMap<String, GRBVar> gurobiVaraibles;
	String[][] result;
	List<String> rC = new ArrayList<String>();
	String[] recommendedCourses;
	private Student TargetStudent;
	private GRBEnv environment;
	private GRBModel model;
	private String errorMessage;
	private double courseLimit = 30.0;
	
	public Solver(HashMap<String,Student> studentHash, HashMap<String,Course> courseHash, String StudentID) {
		studentHashMap = studentHash;
		courseHashMap = courseHash;
		TargetStudent = studentHashMap.get(StudentID);
		errorMessage = "";
	}
	
	public void optimize() {
		try {
			  if (TargetStudent == null) {
				  errorMessage = "Student-preferred courses are unavailable";
				  createResult();
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
		      createResult();
		      model.dispose();
		      environment.dispose();
		    } catch (Exception e) {
		    	log.log(Level.SEVERE, "Exception occured during optimization");
		    }
	}
	
	@SuppressWarnings("unused")
	public String[] createResult() {
		GRBVar variable;
		String StudentID;
		String[] Courses;
		
		if (errorMessage.length()>0) {
			log.log(Level.SEVERE, errorMessage);
			return recommendedCourses;
		} else {
			log.log(Level.INFO, TargetStudent.getStudentID() + ": Optimization was successful");
			Courses = TargetStudent.GetCourses();
			StudentID = TargetStudent.getStudentID();
			result = new String[Courses.length][2];
			int recommendationCounter = 0;
			try {
				if (Courses != null) {
					for (int i=0;i<Courses.length;i++) {
						variable = GetGRBVariable(StudentID, Courses[i]);
						result[i][0] = Courses[i].toString();
						result[i][1] = Double.toString(variable.get(GRB.DoubleAttr.X));
						log.log(Level.INFO, Courses[i] + " " + variable.get(GRB.DoubleAttr.X));
						if (variable.get(GRB.DoubleAttr.X) == 1)
							rC.add(Courses[i].toString());
					}
					for (int i=0;i< rC.size();i++) {
						recommendedCourses[i] = rC.get(i);
					}
					return recommendedCourses;
				}
			} catch (Exception e) {
				log.log(Level.SEVERE,e.getMessage());
				return recommendedCourses;
			}
			return recommendedCourses;
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
				StudentID = tempStudent.getStudentID();
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
			log.log(Level.SEVERE,e.getMessage());
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
			log.log(Level.SEVERE,e.getMessage());
		}
		
	}
	
	private void AddConstraints() {
		ClassLimitConstraint cls = new ClassLimitConstraint();
	    ClassPerSemesterConstraint cps = new ClassPerSemesterConstraint();
		cls.AddClassSizeConstraints(studentHashMap,courseHashMap,TargetStudent,courseLimit, model);
		cps.AddEnrollmentConstraints(studentHashMap,courseHashMap,TargetStudent,courseLimit, model);
	}

	
	public GRBVar GetGRBVariable(String StudentID,String CourseID) {
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
