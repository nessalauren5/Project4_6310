package com.scheduler.dbmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vlanderson on 4/18/15.
 */
@Entity
@Immutable
@Table(name = "studentinfo")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StudentPrefs {

    @Id
    @Column(name="StudentID",columnDefinition = "INT")
    private int studentID;

    @Column(name="NumberCourses",columnDefinition = "INT")
    private int numCourses;

    @Column(name="CList", columnDefinition = "VARCHAR(250)")
    private String courseStr;

    @Column(name = "CreditsEarned")
    private int credits;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }



    public int getNumCourses() {
        return numCourses;
    }

    public void setNumCourses(int numCourses) {
        this.numCourses = numCourses;
    }

    public String getCourseStr() {
        return courseStr;
    }

    public void setCourseStr(String courseStr) {
        this.courseStr = courseStr;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
