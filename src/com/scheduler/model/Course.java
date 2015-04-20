package com.scheduler.model;

import java.util.ArrayList;

/**
 * Created by jrao and vkodali on 4/18/15.
 */
public class Course {

    private String name;
    private String courseID;
	ArrayList<String> Students;
    private ArrayList<Course> prerequisites;
    private ArrayList<Term> termsAvailable = new ArrayList<Term>();

    public String getName() {
        return name;
    }

    public void addTerm(Term t){
        termsAvailable.add(t);
    }

    public boolean isAvailableInTerm(Term t){
        return this.termsAvailable.contains(t);
    }
    public String getCourseID() {
        return courseID;
    }
    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }
    public Course(String id, String name){
        this.name = name;
        this.courseID = id;
    }

    public boolean hasPrereqs(){
        return this.prerequisites!=null;
    }

    public void addPrereq(Course c){
        if(!this.hasPrereqs()){
            this.prerequisites = new ArrayList<Course>();
        }
        this.prerequisites.add(c);
    }
    	
	public Course(String Name) {
		courseID = Name;
		Students = new ArrayList<String>();
		return;
	}
	
	public void AddStudent(String StudentID) {
		Students.add(StudentID);
		return;
	}
	
	public ArrayList<String> GetStudentArray() {
		return Students;
	}

}
