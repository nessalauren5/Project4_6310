package com.scheduler.daoImpl;

import com.scheduler.dao.UserDAO;
import com.scheduler.dbmodel.CoursePriority;
import com.scheduler.dbmodel.StudentPrefs;
import com.scheduler.dbmodel.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class UserDAOImpl implements UserDAO {


    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<CoursePriority> getStudentCourses(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CoursePriority> cq = cb.createQuery(CoursePriority.class);
        Root<CoursePriority> c = cq.from(CoursePriority.class);
        cq.select(c).where(cb.equal(c.get("studentID"), user.getUser_id()));
        TypedQuery<CoursePriority> query = em.createQuery(cq);
        List<CoursePriority> courses = query.getResultList();
        return courses;
    }

    @Override
    public User getUserByCredentials(User user) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> c = cq.from(User.class);
        cq.select(c).where(cb.equal(c.get("username"), user.getUsername()));
        TypedQuery<User> query = em.createQuery(cq);
        User found = query.getSingleResult();
        return found;

    }

    @Override
    public List<StudentPrefs> getAllStudents() {
        CriteriaQuery<StudentPrefs> c = em.getCriteriaBuilder().createQuery(StudentPrefs.class);
        c.from(StudentPrefs.class);
        return em.createQuery(c).getResultList();
    }

}
