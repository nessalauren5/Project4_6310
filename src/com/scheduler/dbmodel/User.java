package com.scheduler.dbmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by vlanderson on 4/13/15.
 */
@Entity
@Table(name = "users")
@XmlRootElement(name="users")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
@org.hibernate.annotations.DynamicInsert(value = true)
public class User implements Serializable{

    @Id
    @Column(name= "userID",columnDefinition = "INT", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    @Column(name="password", columnDefinition= "VARCHAR(30)")
    private String password;

    @Column(name="username",columnDefinition = "VARCHAR(30)")
    private String username;

    @Column(name="firstName",columnDefinition = "VARCHAR(30)")
    private String firstName;

    @Column(name="lastName",columnDefinition = "VARCHAR(30)")
    private String lastName;

    @Column(name="UserType",columnDefinition = "CHAR(13)")
    private String usertype;

    @Column(name="isTA",columnDefinition = "BOOLEAN")
    private boolean isTA;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long user_id) {
        this.userID = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public boolean getIsTA() {
        return isTA;
    }

    public void setIsTA(boolean isTA) {
        this.isTA = isTA;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isTA() {
        return isTA;
    }

    public void setTA(boolean isTA) {
        this.isTA = isTA;
    }

}
