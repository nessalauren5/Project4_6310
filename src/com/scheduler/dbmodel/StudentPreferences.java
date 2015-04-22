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
@Table(name = "studentpreferences")
@XmlRootElement(name="studentpreferences")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class StudentPreferences {

    @Id
    @Column(name="StudentPreferenceID",columnDefinition = "INT", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prefID;

    @Column(name="numberOfCourses",columnDefinition = "INT")
    private int numCourses;

    @Column(name="CourseList", columnDefinition = "VARCHAR(250)")
    private String courseStr;

    @Column(name= "StudentID",columnDefinition = "INT")
    private long studentID;

    public int getPrefID() {
        return prefID;
    }

    public void setPrefID(int prefID) {
        this.prefID = prefID;
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

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }
}
