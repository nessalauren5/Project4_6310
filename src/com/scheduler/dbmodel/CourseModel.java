package com.scheduler.dbmodel;

import com.scheduler.model.Course;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by vlanderson on 2/4/15.
 */
@Entity
@Table(name = "courses")
@XmlRootElement(name="courses")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
@org.hibernate.annotations.DynamicInsert(value = true)
public class CourseModel implements Serializable{

    @Id
    @Column(name="CourseID",columnDefinition = "Integer", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseID;

    @Column(name="CourseName",columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name="Major",columnDefinition = "VARCHAR(10)")
    private String program;

    @Column(name= "Availibility",columnDefinition = "CHAR(11)")
    private String availability;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
