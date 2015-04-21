package com.scheduler.daoImpl;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dbmodel.CourseModel;
import com.scheduler.dbmodel.User;
import javassist.bytecode.stackmap.TypeData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class CourseDAOImpl implements CourseDAO {

    private static final Logger log = Logger.getLogger( TypeData.ClassName.class.getName() );

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

    public List<CourseModel> getCourseListForUser(String[] courses){
//        ArrayList<Integer> courses = new ArrayList<>();


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CourseModel> cq = cb.createQuery(CourseModel.class);
        Root<CourseModel> c = cq.from(CourseModel.class);
        cq.select(c).where(c.get("courseID").in(courses));
        TypedQuery<CourseModel> query = em.createQuery(cq);

       return query.getResultList();
    }

    @Override
    public List<CourseModel> getAllCourses() {
        CriteriaQuery<CourseModel> c = em.getCriteriaBuilder().createQuery(CourseModel.class);
        c.from(CourseModel.class);
        return em.createQuery(c).getResultList();
    }
}
