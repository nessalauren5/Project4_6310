package com.scheduler.dbmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by vlanderson on 4/18/15.
 */
@Entity
@Table(name = "courserecord")
@XmlRootElement(name="courserecord")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class CourseRecord implements Serializable{

    @Id
    @Column(name="CoursesTakenIndex",columnDefinition = "INT", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    @Column(name="studentID",columnDefinition = "Integer")
    private int studentID;

    @Column(name="SemesterID",columnDefinition = "Integer")
    private int semesterID;

    @Column(name="CourseID",columnDefinition = "Integer")
    private int courseID;

    @Column(name="Grade",columnDefinition = "VARCHAR(2)")
    private int grade;

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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
