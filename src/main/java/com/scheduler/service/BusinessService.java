package com.scheduler.service;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dao.UserDAO;
import com.scheduler.model.Course;
import com.scheduler.model.ResultObject;
import com.scheduler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class BusinessService {

    @Autowired
    private UserDAO userdao;

    @Autowired
    private CourseDAO coursedao;


    public ResultObject<User> authUser(User user){
        ResultObject<User> response = new ResultObject<User>();
        User u = userdao.getUserByCredentials(user);

        if(u==null){
            response.setStatus("failed");
        }
        else{
            response.setStatus("success");
        }
        return response;
    }

    public ResultObject<Course> setCourseForUser(User user, Course c){
        ResultObject<Course> response = new ResultObject<Course>();

        if(coursedao.setPriorityForUser(user, c)){
            response.setStatus("success");
        }
        else{
            response.setStatus("failed");
        }
        response.setObject(c);
        return response;
    }

    public ResultObject<ArrayList<Course>> getCourseList(User user){
        ResultObject<ArrayList<Course>> response = new ResultObject<ArrayList<Course>>();
        response.setObject(coursedao.getCourseListForUser(user));
        if(response.getObject()!=null){
            response.setStatus("success");
        }
        else{
            response.setStatus("failed");
        }
        return response;
    }
}
