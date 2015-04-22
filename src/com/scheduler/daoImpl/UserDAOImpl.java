package com.scheduler.daoImpl;

import com.scheduler.dao.UserDAO;
import com.scheduler.dbmodel.StudentInfoView;
import com.scheduler.dbmodel.StudentPreferences;
import com.scheduler.dbmodel.User;
import javassist.bytecode.stackmap.TypeData;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class UserDAOImpl implements UserDAO {

    private static final Logger log = Logger.getLogger( TypeData.ClassName.class.getName() );
    @PersistenceContext
    EntityManager em;

    @Transactional
    public User getUser(String id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> c = cq.from(User.class);
        cq.select(c).where(cb.equal(c.get("userID"), Integer.parseInt(id)));
        TypedQuery<User> query = em.createQuery(cq);
        User found = query.getSingleResult();
        return found;
    }

    @Transactional
    public void setCoursePreferences(String user, String courses) {
        log.log(Level.INFO, "User " + user + " is updating course preferences to: " + courses);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<StudentPreferences> cq = cb.createCriteriaUpdate(StudentPreferences.class);
        Root<StudentPreferences> c = cq.from(StudentPreferences.class);
        cq.set(c.get("courseStr"), courses).where(cb.equal(c.get("studentID"),user));
        em.createQuery(cq).executeUpdate();
    }



    @Transactional
    public StudentInfoView getUserDetails(String id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentInfoView> cq = cb.createQuery(StudentInfoView.class);
        Root<StudentInfoView> c = cq.from(StudentInfoView.class);
        cq.select(c).where(cb.equal(c.get("userID"), id));
        TypedQuery<StudentInfoView> query = em.createQuery(cq);

        StudentInfoView found = query.getSingleResult();
        return found;
    }

    @Override
    public User getUserByCredentials(User user) {
        log.log(Level.INFO,"Querying for user by credentials");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> c = cq.from(User.class);
        cq.select(c).where(cb.equal(c.get("username"), user.getUsername()));
        TypedQuery<User> query = em.createQuery(cq);
        User found = query.getSingleResult();
        return found;

    }

    @Override
    public List<StudentInfoView> getAllStudents() {
        log.log(Level.INFO,"Querying All student prefs");
        CriteriaQuery<StudentInfoView> c = em.getCriteriaBuilder().createQuery(StudentInfoView.class);
        c.from(StudentInfoView.class);
        return em.createQuery(c).getResultList();
    }

}
