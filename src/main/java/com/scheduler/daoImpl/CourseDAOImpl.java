package com.scheduler.daoImpl;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dbmodel.Course;
import com.scheduler.dbmodel.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class CourseDAOImpl implements CourseDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void addUser(User user) {
        em.persist(user);
    }

    @Transactional
    public boolean setPriorityForUser(User u, Course c){

        return true;
    }

    public ArrayList<Course> getCourseListForUser(User u){
        ArrayList<Course> courseList = new ArrayList<Course>();

        return courseList;
    }
}
