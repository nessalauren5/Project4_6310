package com.scheduler.model;

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
@Table(name = "USER")
@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@org.hibernate.annotations.DynamicUpdate(value = true)
@org.hibernate.annotations.DynamicInsert(value = true)
public class User implements Serializable{

    @Id
    @Column(name="user_id",columnDefinition = "UNSIGNED ZEROFILL bigint(20)", updatable=false, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name="pepID", columnDefinition= "VARCHAR(32)", nullable=false)
    private String password;

    @Column(name="username",columnDefinition = "VARCHAR(50)", nullable = false)
    private String username;

    @Column(name="name",columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    public long getUser_id() {
        return user_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
