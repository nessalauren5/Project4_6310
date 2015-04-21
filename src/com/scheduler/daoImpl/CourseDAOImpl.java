package com.scheduler.daoImpl;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dbmodel.CourseModel;
import com.scheduler.dbmodel.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public boolean setPriorityForUser(User u, CourseModel c){

        return true;
    }

    public ArrayList<CourseModel> getCourseListForUser(User u){
        ArrayList<CourseModel> courseList = new ArrayList<CourseModel>();

        return courseList;
    }

    @Override
    public List<CourseModel> getAllCourses() {
        CriteriaQuery<CourseModel> c = em.getCriteriaBuilder().createQuery(CourseModel.class);
        c.from(CourseModel.class);
        return em.createQuery(c).getResultList();
    }
}
