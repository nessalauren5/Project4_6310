package com.scheduler.dbmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vlanderson on 4/18/15.
 */
@Entity
@Table(name = "studentpreferences")
@XmlRootElement(name="studentpreferences")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class StudentPrefs {

    @Id
    @Column(name="prefID",columnDefinition = "INT", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    @Column(name="STUDENTID",columnDefinition = "INT")
    private int studentID;

    @Column(name="SemesterID",columnDefinition = "Integer")
    private int semesterID;

    @Column(name="numberOfCourses",columnDefinition = "INT")
    private int numCourses;

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public int getNumCourses() {
        return numCourses;
    }

    public void setNumCourses(int numCourses) {
        this.numCourses = numCourses;
    }
}
