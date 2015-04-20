package com.scheduler.model;

public class Student {
	private String studentID;
	private int credits;
	private String[] courses;
	
	public void setStudentID(String ID) {
		studentID = ID;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public void setStudentPreferences(String CoursePreferences) {
		courses = CoursePreferences.split(",");
		for (int i=0; i< courses.length; i++) {
			courses[i] = courses[i].trim();
		}
	}
	
	public void SetCredits(String Credits) {
		credits = Integer.parseInt(Credits) + 1;
	}
	
	public int GetCredits() {
		return credits;
	}
	
	public Double GetCourseRestrictions() {
		Double value;
		if (this.credits > 6) {
			value = 3.0;
		} else {
			value = 2.0;
		}
		return value;
	}
	
	public String[] GetCourses() {
		return courses;
	}
}
