package com.scheduler.daoImpl;

import com.scheduler.dao.CourseDAO;
import com.scheduler.model.Course;
import com.scheduler.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class CourseDAOImpl implements CourseDAO {

    @PersistenceContext
    EntityManager em;

    public boolean setPriorityForUser(User u, Course c){
        //TODO: add implementation
        return true;
    }

    public ArrayList<Course> getCourseListForUser(User u){
        ArrayList<Course> courseList = new ArrayList<Course>();

        return courseList;
    }
}
