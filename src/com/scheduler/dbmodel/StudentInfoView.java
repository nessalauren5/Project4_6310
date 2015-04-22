package com.scheduler.dbmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Created by vlanderson on 4/18/15.
 */
@Entity
@Immutable
@Table(name = "studentinfo")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StudentInfoView {

    @Id
    @Column(name="userID",columnDefinition = "INT")
    private int userID;

    @Column(name="NumberCourses",columnDefinition = "INT")
    private int numCourses;

    @Column(name="CList", columnDefinition = "VARCHAR(250)")
    private String courseStr;

    @Column(name = "CreditsEarned")
    private int credits;

    @Column(name="firstName",columnDefinition = "VARCHAR(30)")
    private String firstName;

    @Column(name="lastName",columnDefinition = "VARCHAR(30)")
    private String lastName;

    @Column(name="major", columnDefinition = "VARCHAR(50)")
    private String major;

    public int getUserID() {
        return userID;
    }
    public String getUserIDStr() {
        return String.valueOf(userID);
    }

    public void setUserID(int studentID) {
        this.userID = studentID;
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


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
