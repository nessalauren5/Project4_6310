package com.scheduler.dbmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
/**
 * Created by vlanderson on 4/18/15.
 */
@Entity
@Table(name = "recommendations")
@XmlRootElement(name="recommendations")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
public class Recommendation implements Serializable{

    @Id
    @Column(name="reccID",columnDefinition = "INT", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reccID;

    @Column(name="STUDENTID",columnDefinition = "INT")
    private int studentID;

    @Column(name="date", nullable = true,
            columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertdate = new Date();

    @Column(name="courses",columnDefinition = "VARCHAR(250)")
    private String courseList;

    public int getReccID() {
        return reccID;
    }

    public void setReccID(int reccID) {
        this.reccID = reccID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public Date getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(Date insertdate) {
        this.insertdate = insertdate;
    }

    public String getCourseList() {
        return courseList;
    }

    public void setCourseList(String courseList) {
        this.courseList = courseList;
    }
}
