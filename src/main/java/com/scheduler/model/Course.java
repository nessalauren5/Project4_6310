package com.scheduler.model;

import java.util.ArrayList;

/**
 * Created by vlanderson on 2/4/15.
 */
public class Course {

    private String name;
    private Integer courseID;
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
    public Integer getCourseID() {
        return courseID;
    }
    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }
    public Course(Integer id, String name){
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

}
