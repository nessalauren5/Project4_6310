package com.scheduler.daoImpl;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dbmodel.Course;
import com.scheduler.dbmodel.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class CourseDAOImpl implements CourseDAO {

//    @PersistenceContext
//    EntityManager entityManagerFactory;

    public boolean setPriorityForUser(User u, Course c){
        //TODO: add implementation
        return true;
    }

    public ArrayList<Course> getCourseListForUser(User u){
        ArrayList<Course> courseList = new ArrayList<Course>();

        return courseList;
    }
}
