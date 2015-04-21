package com.scheduler.daoImpl;

import com.scheduler.dao.UserDAO;
import com.scheduler.dbmodel.StudentPrefs;
import com.scheduler.dbmodel.User;
import com.scheduler.model.Student;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public StudentPrefs getUserDetails(String id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentPrefs> cq = cb.createQuery(StudentPrefs.class);
        Root<StudentPrefs> c = cq.from(StudentPrefs.class);
        cq.select(c).where(cb.equal(c.get("userID"), id));
        TypedQuery<StudentPrefs> query = em.createQuery(cq);

        StudentPrefs found = query.getSingleResult();
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
    public List<StudentPrefs> getAllStudents() {
        log.log(Level.INFO,"Querying All student prefs");
        CriteriaQuery<StudentPrefs> c = em.getCriteriaBuilder().createQuery(StudentPrefs.class);
        c.from(StudentPrefs.class);
        return em.createQuery(c).getResultList();
    }

}
