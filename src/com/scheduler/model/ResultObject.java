package com.scheduler.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.NewCookie;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResultObject<T> {

    private T result;
    private String status;
    private String message;
    private String pagination;
    private NewCookie cookie;
    private List<String> errors = new ArrayList<String>();

    public ResultObject(){}
    public ResultObject(T object){
        this.result = object;
    }

    public ResultObject(T object, boolean status){
        this.result = object;
        if(!status) this.status="failed";
        else this.status="success";
    }

    public T getObject() {
        return result;
    }
    public void setObject(T object) {
        this.result = object;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the pagination
     */
    public String getPagination() {
        return pagination;
    }
    /**
     * @param pagination the pagination to set
     */
    public void setPagination(String pagination) {
        this.pagination = pagination;
    }
    public NewCookie getCookie() {
        return cookie;
    }

    public void setCookie(NewCookie cookie) {
        this.cookie = cookie;
    }
    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error){
        errors.add(error);
    }
    public void addErrors(List<String> errs){
        for(int i=0;i<errs.size();i++){
            errors.add(errs.get(i));
        }

    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


}
